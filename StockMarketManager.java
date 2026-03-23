import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Main entry point to the stock market application. It performs the following key functions:
 * 1. Creates random stock transaction data.
 * 2. Reads transaction data into appropriate data structures.
 * 3. Provides a console based user interface that allows users to ask basic analytics style questions.
 */
public class StockMarketManager
{
    List<Stock> allStocks;
    Map<String, List<String>> stocksBySector;
    List<StockTransaction> allStockTransactions;
    Map<String, List<StockTransaction>> transactionsBySymbol;
    StockMarketAnalytics analytics;

    public StockMarketManager()
    {
        init();
    }

    /**
     * load the stock metadata and create information about all traded stocks.
     * @param fileName
     */
    public void loadStockMetadata(String fileName)
    {
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File("stock_metadata.txt"));

            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                
                if (line.startsWith("#"))
                {
                    continue;
                }

                String[] parts = line.split(",");
                String symbol = parts[0];
                String name = parts[1];
                String sector = parts[2];
                int marketCap = Integer.parseInt(parts[3]);
                String country = parts[4];

                Stock currStock = new Stock(symbol, name, sector, marketCap, country);
                allStocks.add(currStock);

                if (stocksBySector.containsKey(sector))
                {
                    stocksBySector.get(sector).add(symbol);
                }
                else
                {
                    List<String> stocksList = new ArrayList<String>();
                    stocksList.add(symbol);
                    stocksBySector.put(sector, stocksList);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error loading file");
        }
        finally
        {
            if (scanner != null)
            {
                scanner.close();
            }
        }
    }

    public static LocalDateTime createRandomDateTime(LocalDateTime start, LocalDateTime end)
    {
        Random rand = new Random();

        long minutesBetween = Duration.between(start, end).toMinutes();
        long randomMinutes = rand.nextInt((int) minutesBetween + 1);

        return start.plusMinutes(randomMinutes);
    }

    public void createStockTransactions(int numTransactions)
    {
        Random rand = new Random();
        LocalDateTime start = LocalDateTime.of(2026, 3, 20, 9, 30);
        LocalDateTime end = LocalDateTime.of(2026, 3, 25, 16, 0);

        for (int i = 0; i < numTransactions; i++)
        {
            int numTradedStocks = allStocks.size();
            int randSymbolIndex = rand.nextInt(numTradedStocks);
            String randSymbol = (allStocks.get(randSymbolIndex)).getSymbol();

            int randVolume = rand.nextInt(200) + 1;
            double randPrice = rand.nextDouble(500) + 50.0;

            LocalDateTime randDateTime = createRandomDateTime(start, end);

            StockTransaction st = new StockTransaction(randSymbol, randVolume, randPrice, randDateTime);
            allStockTransactions.add(st);

            // if (stocksBySector.containsKey(sector))
            //     {
            //         stocksBySector.get(sector).add(symbol);
            //     }
            //     else
            //     {
            //         List<String> stocksList = new ArrayList<String>();
            //         stocksList.add(symbol);
            //         stocksBySector.put(sector, stocksList);
            //     }
            if (transactionsBySymbol.containsKey(randSymbol))
            {// check if the stock already has an entry in transactionsBySymbol
                transactionsBySymbol.get(randSymbol).add(st);
            }
            else
            {
                List<StockTransaction> transactionList = new ArrayList<StockTransaction>();
                transactionList.add(st);
                transactionsBySymbol.put(randSymbol, transactionList);
            }
        }

        // sort by LocalDateTime of StockTransaction in ascending order
        Collections.sort(allStockTransactions, new Comparator<StockTransaction>()
        {
            public int compare(StockTransaction a, StockTransaction b)
            {
                return a.getDatetime().compareTo(b.getDatetime());
            }
        });

        // sets the derived fields which are priceChange and direction
        Map<String, Double> lastTransactionPriceByStock = new HashMap<String, Double>();
        for (StockTransaction transaction: allStockTransactions)
        {
            transaction.setStockTrend(lastTransactionPriceByStock);
        }

        
    }

    public StockMarketAnalytics getStockMarketAnalytics()
    {
        return this.analytics;
    }

    public void init()
    {
        allStocks = new ArrayList<Stock>();
        stocksBySector = new HashMap<String, List<String>>();
        allStockTransactions = new ArrayList<StockTransaction>();
        transactionsBySymbol = new HashMap<String, List<StockTransaction>>();

        // step 1: load the stock metadata and create information about all traded stocks.
        loadStockMetadata("stock_metadata.txt");

        // step 2: generate N stock transactions
        createStockTransactions(100);

        // step 3: create the StockMarketAnalytics instance. 
        analytics = new StockMarketAnalytics(this);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n====STOCKS LISTED====\n");

        for (Stock s: allStocks)
        {
            sb.append(s + "\n");
        }
        sb.append("========\n\n");

        sb.append("====STOCKS BY SECTOR====\n");
        sb.append(stocksBySector + "\n");
        sb.append("========\n");

        sb.append("\n====STOCK TRANSACTIONS====\n");

        for (StockTransaction st: allStockTransactions)
        {
            sb.append(st + "\n");
        }
        sb.append("========\n");

        sb.append("\n====STOCK TRANSACTIONS BY SYMBOL====\n");
        sb.append(transactionsBySymbol + "\n");
        sb.append("========\n");

        return sb.toString();
    }


    public static void main(String[] args)
    {
        StockMarketManager marketManager = new StockMarketManager();
        StockMarketAnalytics analytics = marketManager.getStockMarketAnalytics(); // get a reference to the analytics manager
        System.out.println(marketManager);

        Scanner input = null;

        try
        {
           input = new Scanner(System.in);
            while (true)
            {
                System.out.println("\nChoose an option:");
                System.out.println("1. Stock Summary"); // DONE
                System.out.println("2. Top K Movers by Price Change"); // DONE
                System.out.println("3. Top K Most Active Stocks"); // DONE
                System.out.println("4. Exit"); // DONE


                int choice = input.nextInt();
                System.out.println("choice:" + choice);

                if (choice == 1)
                {
                    System.out.println("Enter a stock symbol:");
                    String symbol = input.next();
                    analytics.getActivityByStock(symbol);

                }
                else if (choice == 2)
                {
                    System.out.println("Enter k:");
                    int k = input.nextInt();
                    analytics.getTopKMovers(k);
                }
                else if (choice == 3)
                {
                    System.out.println("Enter k:");
                    int k = input.nextInt();
                    analytics.getMostActiveStocks(k);
                }
                else if (choice == 4)
                {
                    System.out.println("Exiting StockMarketManager");
                    System.exit(0);
                }
            }
        }
        finally
        {
            if (input != null)
            {
                input.close();
            }
        }
    }
}
