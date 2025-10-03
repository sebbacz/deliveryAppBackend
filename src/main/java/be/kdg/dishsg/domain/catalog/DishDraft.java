package be.kdg.dishsg.domain.catalog;

import java.math.BigDecimal;

public class DishDraft {
    private final String name;
    private final String description;
    private final BigDecimal price;

    public DishDraft(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
