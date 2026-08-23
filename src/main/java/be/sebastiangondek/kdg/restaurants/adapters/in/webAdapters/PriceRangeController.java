package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters;

import be.sebastiangondek.kdg.restaurants.adapters.in.dto.CriteriaEventResponse;
import be.sebastiangondek.kdg.restaurants.adapters.in.dto.PriceRangePointResponse;
import be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.requests.AddCriteriaEventRequest;
import be.sebastiangondek.kdg.restaurants.ports.in.AddCriteriaEventCmd;
import be.sebastiangondek.kdg.restaurants.ports.in.AddCriteriaEventUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.GetCriteriaEventsUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.GetPriceRangeHistoryUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// REST controller for price range histor and criteria for owner.
@RestController
public class PriceRangeController {

    private final AddCriteriaEventUseCase addCriteriaEventUseCase;
    private final GetCriteriaEventsUseCase getCriteriaEventsUseCase;
    private final GetPriceRangeHistoryUseCase getPriceRangeHistoryUseCase;

    public PriceRangeController(AddCriteriaEventUseCase addCriteriaEventUseCase,
                                 GetCriteriaEventsUseCase getCriteriaEventsUseCase,
                                 GetPriceRangeHistoryUseCase getPriceRangeHistoryUseCase) {
        this.addCriteriaEventUseCase = addCriteriaEventUseCase;
        this.getCriteriaEventsUseCase = getCriteriaEventsUseCase;
        this.getPriceRangeHistoryUseCase = getPriceRangeHistoryUseCase;
    }

    // return the single currentl criteria so customers see accurate price range filters
    @GetMapping("/unsecured/price-range/criteria/current")
    public CriteriaEventResponse getCurrentCriteria() {
        return getCriteriaEventsUseCase.getCriteriaEvents().stream()
                .filter(e -> !e.getEffectiveAt().isAfter(java.time.LocalDateTime.now()))
                .reduce((a, b) -> a.getEffectiveAt().isAfter(b.getEffectiveAt()) ? a : b)
                .map(e -> new CriteriaEventResponse(e.getId(), e.getEffectiveAt(), e.getCheapMax(), e.getRegularMax(), e.getExpensiveMax()))
                .orElse(null);
    }

    // get price range history for a restaurant
    @GetMapping("/unsecured/restaurants/{restaurantId}/price-range-history")
    public List<PriceRangePointResponse> getPriceRangeHistory(@PathVariable UUID restaurantId) {
        return getPriceRangeHistoryUseCase.getPriceRangeHistory(restaurantId).stream()
                .map(p -> new PriceRangePointResponse(p.month, p.priceRange.name(), p.averagePrice))
                .collect(Collectors.toList());
    }

    //  Get all price range criteria events  for owner
    @GetMapping("/api/price-range/criteria")
    @PreAuthorize("hasAuthority('owner')")
    public List<CriteriaEventResponse> getCriteriaEvents() {
        return getCriteriaEventsUseCase.getCriteriaEvents().stream()
                .map(e -> new CriteriaEventResponse(e.getId(), e.getEffectiveAt(), e.getCheapMax(), e.getRegularMax(), e.getExpensiveMax()))
                .collect(Collectors.toList());
    }

    //  Add a new price range criteria for owner
    @PostMapping("/api/price-range/criteria")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public void addCriteriaEvent(@RequestBody AddCriteriaEventRequest request) {
        addCriteriaEventUseCase.addCriteriaEvent(
                new AddCriteriaEventCmd(request.effectiveAt, request.cheapMax, request.regularMax, request.expensiveMax));
    }
}
