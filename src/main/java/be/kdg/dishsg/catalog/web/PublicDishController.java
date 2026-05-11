package be.kdg.dishsg.catalog.web;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.GetPublishedDishesUseCase;
import be.kdg.dishsg.catalog.web.dto.DishResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// US15: public endpoint — no auth required (/unsecured/** is permitted in SecurityConfig)
@RestController
@RequestMapping("/unsecured/restaurants/{restaurantId}/dishes")
public class PublicDishController {

    private final GetPublishedDishesUseCase getPublishedDishesUseCase;

    public PublicDishController(GetPublishedDishesUseCase getPublishedDishesUseCase) {
        this.getPublishedDishesUseCase = getPublishedDishesUseCase;
    }

    @GetMapping
    public List<DishResponse> getPublishedDishes(@PathVariable UUID restaurantId) {
        return getPublishedDishesUseCase.getPublishedDishes(restaurantId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private DishResponse toResponse(Dish dish) {
        DishResponse dto = new DishResponse();
        dto.id = dish.getId();
        dto.restaurantId = dish.getRestaurantId();
        dto.name = dish.getName();
        dto.type = dish.getType() != null ? dish.getType().name() : null;
        dto.foodTags = dish.getFoodTags();
        dto.description = dish.getDescription();
        dto.price = dish.getPrice();
        dto.pictureUrl = dish.getPictureUrl();
        dto.inStock = dish.isInStock();
        dto.state = dish.getState().name();
        dto.scheduledAt = dish.getScheduledAt();
        return dto;
    }
}
