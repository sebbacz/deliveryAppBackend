package be.kdg.dishsg.pricerange.app;

import be.kdg.dishsg.catalog.ports.out.DishRepositoryPort;
import be.kdg.dishsg.pricerange.domain.PriceRangeCriteriaEvent;
import be.kdg.dishsg.pricerange.domain.PriceRangePoint;
import be.kdg.dishsg.pricerange.ports.in.AddCriteriaEventUseCase;
import be.kdg.dishsg.pricerange.ports.in.GetPriceRangeHistoryUseCase;
import be.kdg.dishsg.pricerange.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;

@Service
public class PriceRangeService implements AddCriteriaEventUseCase, GetPriceRangeHistoryUseCase {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final PriceRangeCriteriaEventRepositoryPort criteriaRepo;
    private final DishRepositoryPort dishRepo;

    public PriceRangeService(PriceRangeCriteriaEventRepositoryPort criteriaRepo,
                              DishRepositoryPort dishRepo) {
        this.criteriaRepo = criteriaRepo;
        this.dishRepo = dishRepo;
    }

    @Override
    public void addCriteriaEvent(LocalDateTime effectiveAt, double cheapMax, double regularMax, double expensiveMax) {
        PriceRangeCriteriaEvent event = new PriceRangeCriteriaEvent(
                UUID.randomUUID(), effectiveAt, cheapMax, regularMax, expensiveMax);
        criteriaRepo.save(event);
    }

    @Override
    public List<PriceRangePoint> getPriceRangeHistory(UUID restaurantId) {
        List<PriceRangeCriteriaEvent> events = criteriaRepo.findAllOrderedByEffectiveAt();
        if (events.isEmpty()) {
            return List.of();
        }

        // Calculate current average price of live dishes for this restaurant
        OptionalDouble avg = dishRepo.findLiveByRestaurantId(restaurantId).stream()
                .mapToDouble(d -> d.getPrice())
                .average();
        double averagePrice = avg.orElse(0.0);

        // Generate monthly data points from first event to now
        YearMonth start = YearMonth.from(events.get(0).getEffectiveAt());
        YearMonth current = YearMonth.now();

        List<PriceRangePoint> history = new ArrayList<>();
        YearMonth month = start;
        while (!month.isAfter(current)) {
            LocalDateTime monthStart = month.atDay(1).atStartOfDay();
            // Find applicable criteria: most recent event with effectiveAt <= monthStart
            PriceRangeCriteriaEvent applicable = null;
            for (PriceRangeCriteriaEvent event : events) {
                if (!event.getEffectiveAt().isAfter(monthStart)) {
                    applicable = event;
                }
            }
            if (applicable != null) {
                history.add(new PriceRangePoint(
                        month.format(MONTH_FORMATTER),
                        applicable.classify(averagePrice),
                        Math.round(averagePrice * 100.0) / 100.0
                ));
            }
            month = month.plusMonths(1);
        }
        return history;
    }
}
