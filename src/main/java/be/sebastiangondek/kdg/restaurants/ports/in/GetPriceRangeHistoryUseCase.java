package be.sebastiangondek.kdg.restaurants.ports.in;

import be.sebastiangondek.kdg.restaurants.domain.PriceRangePoint;

import java.util.List;
import java.util.UUID;

// In-port for see price range classification month by month.
public interface GetPriceRangeHistoryUseCase {
    List<PriceRangePoint> getPriceRangeHistory(UUID restaurantId);
}
