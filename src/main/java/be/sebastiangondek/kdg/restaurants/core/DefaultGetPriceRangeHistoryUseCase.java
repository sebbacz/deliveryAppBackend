package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.ports.out.DishRepositoryPort;
import be.sebastiangondek.kdg.restaurants.domain.PriceRangeCriteriaEvent;
import be.sebastiangondek.kdg.restaurants.domain.PriceRangePoint;
import be.sebastiangondek.kdg.restaurants.ports.in.GetPriceRangeHistoryUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.PriceRangeCriteriaEventRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

// Service computing the monthly price range history for a restaurant using event-sourced criteria.
@Transactional(readOnly = true)
@Service
public class DefaultGetPriceRangeHistoryUseCase implements GetPriceRangeHistoryUseCase {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    private final PriceRangeCriteriaEventRepositoryPort criteriaRepo;
    private final DishRepositoryPort dishRepo;

    public DefaultGetPriceRangeHistoryUseCase(PriceRangeCriteriaEventRepositoryPort criteriaRepo,
                                               DishRepositoryPort dishRepo) {
        this.criteriaRepo = criteriaRepo;
        this.dishRepo = dishRepo;
    }

    @Override
    public List<PriceRangePoint> getPriceRangeHistory(UUID restaurantId) {
        List<PriceRangeCriteriaEvent> events = criteriaRepo.findAllOrderedByEffectiveAt();
        if (events.isEmpty()) {
            return List.of();
        }

        // Calculate current average price of live dishes for this restaurant
        OptionalDouble avg = dishRepo.findLiveByRestaurantId(restaurantId).stream()
                .mapToDouble(d -> d.getEffectivePrice())
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
