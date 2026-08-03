package be.kdg.dishsg.pricerange.adapters.in.webAdapters;

import be.kdg.dishsg.pricerange.adapters.in.dto.CriteriaEventResponse;
import be.kdg.dishsg.pricerange.adapters.in.dto.PriceRangePointResponse;
import be.kdg.dishsg.pricerange.adapters.in.webAdapters.requests.AddCriteriaEventRequest;
import be.kdg.dishsg.pricerange.ports.in.AddCriteriaEventUseCase;
import be.kdg.dishsg.pricerange.ports.in.GetCriteriaEventsUseCase;
import be.kdg.dishsg.pricerange.ports.in.GetPriceRangeHistoryUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// REST controller exposing price range history (public) and criteria management (owner-secured).
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

    // Public: return the single currently-effective criteria so customers see accurate price range filters
    @GetMapping("/unsecured/price-range/criteria/current")
    public CriteriaEventResponse getCurrentCriteria() {
        return getCriteriaEventsUseCase.getCriteriaEvents().stream()
                .filter(e -> !e.getEffectiveAt().isAfter(java.time.LocalDateTime.now()))
                .reduce((a, b) -> a.getEffectiveAt().isAfter(b.getEffectiveAt()) ? a : b)
                .map(e -> new CriteriaEventResponse(e.getId(), e.getEffectiveAt(), e.getCheapMax(), e.getRegularMax(), e.getExpensiveMax()))
                .orElse(null);
    }

    // US26: Get price range history for a restaurant (public)
    @GetMapping("/unsecured/restaurants/{restaurantId}/price-range-history")
    public List<PriceRangePointResponse> getPriceRangeHistory(@PathVariable UUID restaurantId) {
        return getPriceRangeHistoryUseCase.getPriceRangeHistory(restaurantId).stream()
                .map(p -> new PriceRangePointResponse(p.month, p.priceRange.name(), p.averagePrice))
                .collect(Collectors.toList());
    }

    // US32: Get all price range criteria events (owner-secured as proxy for admin)
    @GetMapping("/api/price-range/criteria")
    @PreAuthorize("hasAuthority('owner')")
    public List<CriteriaEventResponse> getCriteriaEvents() {
        return getCriteriaEventsUseCase.getCriteriaEvents().stream()
                .map(e -> new CriteriaEventResponse(e.getId(), e.getEffectiveAt(), e.getCheapMax(), e.getRegularMax(), e.getExpensiveMax()))
                .collect(Collectors.toList());
    }

    // US32: Add a new price range criteria event (owner-secured as proxy for admin)
    @PostMapping("/api/price-range/criteria")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public void addCriteriaEvent(@RequestBody AddCriteriaEventRequest request) {
        addCriteriaEventUseCase.addCriteriaEvent(
                request.effectiveAt, request.cheapMax, request.regularMax, request.expensiveMax);
    }
}
