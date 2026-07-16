package be.kdg.dishsg.order.domain;

public record DeliveryAddress(
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {}
