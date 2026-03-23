/**
 * Represents an individual stock traded on the StockMarketManager.
 * It has the stock symbol and associated metadata.
 */
public class Stock
{
    private String symbol;
    private String name;
    private String sector;
    private int marketCap;
    private String country;

    public Stock(
        String symbol,
        String name,
        String sector,
        int marketCap,
        String country
    )
    {
        this.symbol = symbol;
        this.name = name;
        this.sector = sector;
        this.marketCap = marketCap;
        this.country = country;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSector()
    {
        return sector;
    }

    public void setSector(String sector)
    {
        this.sector = sector;
    }

    public int getMarketCap()
    {
        return marketCap;
    }

    public void setMarketCap(int marketCap)
    {
        this.marketCap = marketCap;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return symbol + "," + name + "," + sector + "," + marketCap + "," + country;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {// same object
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {// object is null or of different type, return false
            return false;
        }
        
        // cast to type Stock and compare with the current
        Stock other = (Stock) obj;

        if (symbol.equals(other.symbol) && country.equals(other.country))
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
        result = 31 * result + country.hashCode();

        return result;
    }

}