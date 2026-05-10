package be.kdg.dishsg.catalog.web;

import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.DishType;
import be.kdg.dishsg.catalog.ports.in.ApplyPendingChangesUseCase;
import be.kdg.dishsg.catalog.ports.in.PublishDishUseCase;
import be.kdg.dishsg.catalog.ports.in.SaveDishDraftUseCase;
import be.kdg.dishsg.catalog.ports.in.ScheduleDishChangesUseCase;
import be.kdg.dishsg.catalog.ports.in.UnpublishDishUseCase;
import be.kdg.dishsg.catalog.ports.in.UpdateDishStockUseCase;
import be.kdg.dishsg.catalog.web.dto.DishDraftRequest;
import be.kdg.dishsg.catalog.web.dto.DishResponse;
import be.kdg.dishsg.catalog.web.dto.ScheduleChangesRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final SaveDishDraftUseCase saveDishDraftUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;
    private final UpdateDishStockUseCase updateDishStockUseCase;
    private final ApplyPendingChangesUseCase applyPendingChangesUseCase;
    private final ScheduleDishChangesUseCase scheduleDishChangesUseCase;

    public DishController(SaveDishDraftUseCase saveDishDraftUseCase,
                          PublishDishUseCase publishDishUseCase,
                          UnpublishDishUseCase unpublishDishUseCase,
                          UpdateDishStockUseCase updateDishStockUseCase,
                          ApplyPendingChangesUseCase applyPendingChangesUseCase,
                          ScheduleDishChangesUseCase scheduleDishChangesUseCase) {
        this.saveDishDraftUseCase = saveDishDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.updateDishStockUseCase = updateDishStockUseCase;
        this.applyPendingChangesUseCase = applyPendingChangesUseCase;
        this.scheduleDishChangesUseCase = scheduleDishChangesUseCase;
    }

    // US3: Edit dish as draft
    @PostMapping("/draft")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public DishResponse saveDraft(@RequestBody DishDraftRequest request) {
        Dish input = new Dish(
                request.id,
                request.restaurantId,
                request.name,
                request.type != null ? DishType.valueOf(request.type) : null,
                request.foodTags,
                request.description,
                request.price,
                request.pictureUrl,
                true,
                Dish.DishState.DRAFT
        );
        return toResponse(saveDishDraftUseCase.saveDraft(input));
    }

    // US4: Publish dish
    @PostMapping("/{id}/publish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void publish(@PathVariable UUID id) {
        publishDishUseCase.publishDish(id);
    }

    // US5: Unpublish dish
    @PostMapping("/{id}/unpublish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void unpublish(@PathVariable UUID id) {
        unpublishDishUseCase.unpublishDish(id);
    }

    // US8: Mark dish out of stock / back in stock
    @PostMapping("/{id}/out-of-stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void markOutOfStock(@PathVariable UUID id) {
        updateDishStockUseCase.markOutOfStock(id);
    }

    @PostMapping("/{id}/back-in-stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void markBackInStock(@PathVariable UUID id) {
        updateDishStockUseCase.markBackInStock(id);
    }

    // US6: Apply all pending changes in one action
    @PostMapping("/apply-changes/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void applyPendingChanges(@PathVariable UUID restaurantId) {
        applyPendingChangesUseCase.applyPendingChanges(restaurantId);
    }

    // US7: Schedule pending changes to go live at a chosen time
    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void scheduleChanges(@RequestBody ScheduleChangesRequest request) {
        scheduleDishChangesUseCase.scheduleChanges(request.restaurantId, request.scheduledAt);
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
