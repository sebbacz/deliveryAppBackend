package be.kdg.dishsg.pricerange.web.dto;

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
