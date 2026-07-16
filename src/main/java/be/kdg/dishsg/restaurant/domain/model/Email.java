package be.kdg.dishsg.restaurant.domain.model;

public record Email(String value) {
    public Email {
        if (value == null || value.isBlank() || !value.contains("@"))
            throw new IllegalArgumentException("Invalid email: " + value);
    }
}
