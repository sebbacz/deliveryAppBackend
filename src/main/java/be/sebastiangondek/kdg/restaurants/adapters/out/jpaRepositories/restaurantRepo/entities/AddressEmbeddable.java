package be.sebastiangondek.kdg.restaurants.adapters.out.jpaRepositories.restaurantRepo.entities;

import jakarta.persistence.Embeddable;

// JPA  holding a street address stored  in the restaurants table.
@Embeddable
public class AddressEmbeddable {

    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    public AddressEmbeddable() {}

    public AddressEmbeddable(String street, String number, String postalCode, String city, String country) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet()     { return street; }
    public String getNumber()     { return number; }
    public String getPostalCode() { return postalCode; }
    public String getCity()       { return city; }
    public String getCountry()    { return country; }
}
