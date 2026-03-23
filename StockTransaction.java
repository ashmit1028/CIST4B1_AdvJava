import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents an individual stock transaction.
 * Accumulated to generate various analytics.
 */
public class StockTransaction
{
    // symbol,volume,price,priceChange,direction,datetime
    private String symbol;
    private int volume;
    private double price;
    private double priceChange;
    /** Can be up or down based on the previous S */
    private String direction;
    /** DateTime without a time zone, can mean local time. */
    private LocalDateTime datetime;

    public StockTransaction(
        String symbol,
        int volume,
        double price,
        LocalDateTime datetime
    )
    {
        this.symbol = symbol;
        this.volume = volume;
        this.price = price;
        this.datetime = datetime;
    }

    /**
     * sets the derived fields which are priceChange and direction
     */
    public void setStockTrend(Map<String, Double> lastTransactionPriceByStock)
    {
        if (lastTransactionPriceByStock.containsKey(symbol))
        {
            priceChange = price - lastTransactionPriceByStock.get(symbol);
        }
        else
        {
            // No previous recorded transaction; therefore, we assume this
            // is the first transaction for the symbol.
            priceChange = 0;
        }

        // if priceChange is 0
        if (priceChange > 0)
        {
            direction = "UP";
        }
        else if (priceChange < 0)
        {
            direction = "DOWN";
        }
        else
        {
            direction = "UNCHANGED";
        }

        lastTransactionPriceByStock.put(symbol, price);
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPriceChange()
    {
        return priceChange;
    }

    public String getDirection()
    {
        return direction;
    }

    public LocalDateTime getDatetime()
    {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime)
    {
        this.datetime = datetime;
    }

    @Override
    public String toString()
    {
        return symbol + "," + volume + "," + String.format("%.2f", price) + "," + String.format("%.2f", priceChange) + "," + direction + "," + datetime;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {// same object
            return true;
        }

        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        StockTransaction other = (StockTransaction) obj;

        if (symbol.equals(other.symbol) && datetime.equals(other.datetime))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 31 * result + symbol.hashCode();
        result = 31 * result + datetime.hashCode();

        return result;
    }
}
