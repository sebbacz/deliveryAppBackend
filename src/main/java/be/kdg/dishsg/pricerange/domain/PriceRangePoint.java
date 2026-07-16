package be.kdg.dishsg.pricerange.domain;

// Value object representing a restaurant's price range classification for a specific month.
public class PriceRangePoint {
    public final String month;
    public final PriceRange priceRange;
    public final double averagePrice;

    public PriceRangePoint(String month, PriceRange priceRange, double averagePrice) {
        this.month = month;
        this.priceRange = priceRange;
        this.averagePrice = averagePrice;
    }
}
