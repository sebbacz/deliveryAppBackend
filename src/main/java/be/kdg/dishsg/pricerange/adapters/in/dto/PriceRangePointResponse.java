package be.kdg.dishsg.pricerange.adapters.in.dto;

// Response DTO for a monthly price range data point used in the history chart.
public class PriceRangePointResponse {
    public String month;
    public String priceRange;
    public double averagePrice;

    public PriceRangePointResponse(String month, String priceRange, double averagePrice) {
        this.month = month;
        this.priceRange = priceRange;
        this.averagePrice = averagePrice;
    }
}
