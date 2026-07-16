package be.kdg.dishsg.catalog.adapters.in.webAdapters;

import be.kdg.dishsg.catalog.adapters.in.dto.DishResponse;
import be.kdg.dishsg.catalog.adapters.in.webAdapters.requests.DishDraftRequest;
import be.kdg.dishsg.catalog.adapters.in.webAdapters.requests.ScheduleChangesRequest;
import be.kdg.dishsg.catalog.domain.Dish;
import be.kdg.dishsg.catalog.domain.DishData;
import be.kdg.dishsg.catalog.domain.DishType;
import be.kdg.dishsg.catalog.ports.in.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final CreateDishDraftUseCase createDishDraftUseCase;
    private final EditDishDraftUseCase editDishDraftUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;
    private final UpdateDishStockUseCase updateDishStockUseCase;
    private final ApplyPendingChangesUseCase applyPendingChangesUseCase;
    private final ScheduleDishChangesUseCase scheduleDishChangesUseCase;
    private final GetOwnerDishesUseCase getOwnerDishesUseCase;

    public DishController(CreateDishDraftUseCase createDishDraftUseCase,
                          EditDishDraftUseCase editDishDraftUseCase,
                          PublishDishUseCase publishDishUseCase,
                          UnpublishDishUseCase unpublishDishUseCase,
                          UpdateDishStockUseCase updateDishStockUseCase,
                          ApplyPendingChangesUseCase applyPendingChangesUseCase,
                          ScheduleDishChangesUseCase scheduleDishChangesUseCase,
                          GetOwnerDishesUseCase getOwnerDishesUseCase) {
        this.createDishDraftUseCase = createDishDraftUseCase;
        this.editDishDraftUseCase = editDishDraftUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.updateDishStockUseCase = updateDishStockUseCase;
        this.applyPendingChangesUseCase = applyPendingChangesUseCase;
        this.scheduleDishChangesUseCase = scheduleDishChangesUseCase;
        this.getOwnerDishesUseCase = getOwnerDishesUseCase;
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAuthority('owner')")
    public List<DishResponse> getOwnerDishes(@PathVariable UUID restaurantId) {
        return getOwnerDishesUseCase.getOwnerDishes(restaurantId).stream()
                .map(DishController::toResponse)
                .toList();
    }

    @PostMapping("/draft")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public DishResponse createDraft(@RequestBody DishDraftRequest request) {
        SaveDishDraftCmd cmd = new SaveDishDraftCmd(
                request.restaurantId,
                request.name,
                request.type != null ? DishType.valueOf(request.type) : null,
                request.foodTags,
                request.description,
                request.price,
                request.pictureUrl
        );
        return toResponse(createDishDraftUseCase.createDraft(cmd));
    }

    @PutMapping("/{id}/draft")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('owner')")
    public DishResponse updateDraft(@PathVariable UUID id, @RequestBody DishDraftRequest request) {
        UpdateDishDraftCmd cmd = new UpdateDishDraftCmd(
                id,
                request.name,
                request.type != null ? DishType.valueOf(request.type) : null,
                request.foodTags,
                request.description,
                request.price,
                request.pictureUrl
        );
        return toResponse(editDishDraftUseCase.updateDraft(cmd));
    }

    @PostMapping("/{id}/publish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void publish(@PathVariable UUID id) {
        publishDishUseCase.publishDish(id);
    }

    @PostMapping("/{id}/unpublish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void unpublish(@PathVariable UUID id) {
        unpublishDishUseCase.unpublishDish(id);
    }

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

    @PostMapping("/apply-changes/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void applyPendingChanges(@PathVariable UUID restaurantId) {
        applyPendingChangesUseCase.applyPendingChanges(restaurantId);
    }

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void scheduleChanges(@RequestBody ScheduleChangesRequest request) {
        scheduleDishChangesUseCase.scheduleChanges(new ScheduleDishChangesCmd(request.restaurantId, request.scheduledAt));
    }

    static DishResponse toResponse(Dish dish) {
        DishResponse dto = new DishResponse();
        dto.id = dish.getId();
        dto.restaurantId = dish.getRestaurantId();
        dto.name = dish.getEffectiveName();
        dto.type = dish.getEffectiveType() != null ? dish.getEffectiveType().name() : null;
        dto.foodTags = dish.getEffectiveFoodTags();
        dto.description = dish.getEffectiveDescription();
        dto.price = dish.getEffectivePrice();
        dto.pictureUrl = dish.getEffectivePictureUrl();
        dto.inStock = dish.isInStock();
        dto.state = dish.getState().name();
        dto.scheduledAt = dish.getScheduledAt();

        if (dish.getState() == Dish.DishState.LIVE_WITH_PENDING) {
            DishData pending = dish.getDraft();
            DishResponse.PendingDraft pd = new DishResponse.PendingDraft();
            pd.name = pending.name();
            pd.type = pending.type() != null ? pending.type().name() : null;
            pd.foodTags = pending.foodTags();
            pd.description = pending.description();
            pd.price = pending.price();
            pd.pictureUrl = pending.pictureUrl();
            dto.pendingDraft = pd;
        }
        return dto;
    }
}
