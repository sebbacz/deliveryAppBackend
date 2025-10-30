package be.kdg.dishsg.dish.web;


import be.kdg.dishsg.dish.app.ApplyPendingDishesServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final ApplyPendingDishesServiceImpl applyPendingDishesService;

    public DishController(ApplyPendingDishesServiceImpl applyPendingDishesService) {
        this.applyPendingDishesService = applyPendingDishesService;
    }

    @PostMapping("/apply-changes/{restaurantId}")
    @PreAuthorize("hasAuthority('owner')")
    public void applyPendingChanges(@PathVariable UUID restaurantId) {
        applyPendingDishesService.applyPendingChanges(restaurantId);
    }
}
