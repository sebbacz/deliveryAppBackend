package be.kdg.dishsg.catalog.adapter.out;


import be.kdg.dishsg.catalog.domain.DishState;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dishes")
public class DishJpaEntity {
    @Id
    private UUID id;
    private UUID restaurantId;
    private String name;
    private String description;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private DishState state;

    private boolean inStock;

    protected DishJpaEntity() {} // JPA
}
