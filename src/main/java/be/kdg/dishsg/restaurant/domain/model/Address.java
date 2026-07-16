package be.kdg.dishsg.restaurant.domain.model;

// Value object representing a physical street address used by a restaurant.
public class Address {

    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    public Address() {}

    public Address(String street, String number, String postalCode, String city, String country) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
