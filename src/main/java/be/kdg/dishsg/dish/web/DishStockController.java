package be.kdg.dishsg.dish.web;


import be.kdg.dishsg.dish.app.UpdateDishStockServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
public class DishStockController {

    private final UpdateDishStockServiceImp stockService;

    public DishStockController(UpdateDishStockServiceImp stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/{id}/out-of-stock")
    @PreAuthorize("hasAuthority('owner')")
    public void markOutOfStock(@PathVariable UUID id) {
        stockService.markOutOfStock(id);
    }

    @PostMapping("/{id}/back-in-stock")
    @PreAuthorize("hasAuthority('owner')")
    public void markBackInStock(@PathVariable UUID id) {
        stockService.markBackInStock(id);
    }
}
