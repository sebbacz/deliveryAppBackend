package be.kdg.dishsg.pricerange.domain;

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
