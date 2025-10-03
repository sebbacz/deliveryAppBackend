package be.kdg.dishsg.domain.order;

public class DeliveryAddress {
    private final String street;
    private final String city;
    private final String postalCode;

    public DeliveryAddress(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }
}
