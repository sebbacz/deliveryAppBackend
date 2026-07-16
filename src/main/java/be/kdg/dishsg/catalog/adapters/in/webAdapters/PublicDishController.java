package be.kdg.dishsg.catalog.adapters.in.webAdapters;

import be.kdg.dishsg.catalog.adapters.in.dto.DishResponse;
import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.ports.in.GetPublishedDishesUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// REST controller exposing the live dish menu for a restaurant without authentication.
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
                .map(DishController::toResponse)
                .collect(Collectors.toList());
    }
}
