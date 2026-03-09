package be.kdg.dishsg.catalog.web;


import be.kdg.dishsg.catalog.app.DishDraftService;
import be.kdg.dishsg.catalog.app.DishPublishingService;
import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.web.dto.DishDraftRequest;
import be.kdg.dishsg.catalog.web.dto.DishResponse;
import org.springframework.web.bind.annotation.*;

@RestController("catalogDishController")
@RequestMapping("/api/dishes")
public class DishController {

    private final DishDraftService draftService;
    private final DishPublishingService publishService;

    public DishController(DishDraftService draftService, DishPublishingService publishService) {
        this.draftService = draftService;
        this.publishService = publishService;
    }

    @PostMapping("/draft")
    public DishResponse saveDraft(@RequestBody DishDraftRequest request) {
        Dish dish = new Dish(
                request.id,
                request.restaurantId,
                request.name,
                request.description,
                request.price,
                Dish.DishState.DRAFT
        );
        Dish saved = draftService.saveDraft(dish);
        return toResponse(saved);
    }

    @PostMapping("/{id}/publish")
    public void publish(@PathVariable String id) {
        publishService.publishDish(id);
    }

    @PostMapping("/{id}/unpublish")
    public void unpublish(@PathVariable String id) {
        publishService.unpublishDish(id);
    }

    private DishResponse toResponse(Dish dish) {
        DishResponse dto = new DishResponse();
        dto.id = dish.getId();
        dto.name = dish.getName();
        dto.description = dish.getDescription();
        dto.price = dish.getPrice();
        dto.state = dish.getState().name();
        return dto;
    }
}
