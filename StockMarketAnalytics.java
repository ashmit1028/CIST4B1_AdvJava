import java.util.*;

/**
 * Supports various analytics functions based on StockTransaction data.
 * It is used from the StockMarketManager.
 */
public class StockMarketAnalytics 
{
    private StockMarketManager manager;

    public StockMarketAnalytics(
        StockMarketManager manager
    )
    {
        this.manager = manager;
    }

    /**
     * Get aggregate statistics for a given stock's activity.
     * @param symbol
     */
    public void getActivityByStock(String symbol)
    {
        List<StockTransaction> transactionsBySymbol = manager.transactionsBySymbol.get(symbol);
        int numTrades = transactionsBySymbol.size();

        int totalVolume = 0;
        for (StockTransaction transaction: transactionsBySymbol)
        {
            totalVolume += transaction.getVolume();
        }

        int totalDollarValue = 0;
        for (StockTransaction transaction: transactionsBySymbol)
        {
            totalDollarValue += transaction.getVolume() * transaction.getPrice();
        }

        System.out.println("symbol:" + symbol);
        System.out.println("numTrades:" + numTrades);
        System.out.println("totalVolume:" + totalVolume);
        System.out.println("totalDollarValue:" + totalDollarValue);
    }

    /**
     * Get top k movers by absolute value of priceChange.
     * @param k
     */
    public void getTopKMovers(int k)
    {
        Collections.sort(manager.allStockTransactions, new Comparator<StockTransaction>()
        {
            public int compare(StockTransaction a, StockTransaction b)
            {
                double aChange = Math.abs(a.getPriceChange());
                double bChange = Math.abs(b.getPriceChange());

                if (aChange > bChange) return -1;
                if (aChange < bChange) return 1;
                return 0;
            }
        });

        int limit = k;
        if (k > manager.allStockTransactions.size())
        {
            limit = manager.allStockTransactions.size();
        }

        System.out.println("\nTop " + limit + " Movers:");

        for (int i = 0; i < limit; i++)
        {
            System.out.println(manager.allStockTransactions.get(i));
        }
    }

    /**
     * Get the top k stocks which have the most transactions.
     */
    public void getMostActiveStocks(int k)
    {
        PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>()
        {
            public int compare(String a, String b)
            {
                int countA = manager.transactionsBySymbol.get(a).size();
                int countB = manager.transactionsBySymbol.get(b).size();

                return countB - countA;
            }
        });

        // We are adding N stocks each with O(log(N)) time complexity.
        // This for loop takes O(Nlog(N)) time.
        for (String symbol: manager.transactionsBySymbol.keySet())
        {
            pq.add(symbol);
        }

        System.out.println("\nTop " + k + " Most Active Stocks: ");
        for (int i = 0; i < k; i++)
        {
            String symbol = pq.poll();
            int count = manager.transactionsBySymbol.get(symbol).size();

            System.out.println(symbol + ": " + count);
        }
    }

}