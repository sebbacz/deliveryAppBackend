package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters;

import be.sebastiangondek.kdg.restaurants.adapters.in.dto.DishResponse;
import be.sebastiangondek.kdg.restaurants.ports.in.GetPublishedDishesUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// REST controller for  live dish menu for a restaurant without authentication.
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
