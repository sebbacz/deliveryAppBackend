package be.kdg.dishsg.pricerange.web;

import be.kdg.dishsg.pricerange.domain.PriceRangePoint;
import be.kdg.dishsg.pricerange.ports.in.AddCriteriaEventUseCase;
import be.kdg.dishsg.pricerange.ports.in.GetPriceRangeHistoryUseCase;
import be.kdg.dishsg.pricerange.web.dto.AddCriteriaEventRequest;
import be.kdg.dishsg.pricerange.web.dto.PriceRangePointResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class PriceRangeController {

    private final AddCriteriaEventUseCase addCriteriaEventUseCase;
    private final GetPriceRangeHistoryUseCase getPriceRangeHistoryUseCase;

    public PriceRangeController(AddCriteriaEventUseCase addCriteriaEventUseCase,
                                 GetPriceRangeHistoryUseCase getPriceRangeHistoryUseCase) {
        this.addCriteriaEventUseCase = addCriteriaEventUseCase;
        this.getPriceRangeHistoryUseCase = getPriceRangeHistoryUseCase;
    }

    // US26: Get price range history for a restaurant (public)
    @GetMapping("/unsecured/restaurants/{restaurantId}/price-range-history")
    public List<PriceRangePointResponse> getPriceRangeHistory(@PathVariable UUID restaurantId) {
        return getPriceRangeHistoryUseCase.getPriceRangeHistory(restaurantId).stream()
                .map(p -> new PriceRangePointResponse(p.month, p.priceRange.name(), p.averagePrice))
                .collect(Collectors.toList());
    }

    // Admin: Add a new price range criteria event (owner-secured as proxy for admin)
    @PostMapping("/api/price-range/criteria")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public void addCriteriaEvent(@RequestBody AddCriteriaEventRequest request) {
        addCriteriaEventUseCase.addCriteriaEvent(
                request.effectiveAt, request.cheapMax, request.regularMax, request.expensiveMax);
    }
}
