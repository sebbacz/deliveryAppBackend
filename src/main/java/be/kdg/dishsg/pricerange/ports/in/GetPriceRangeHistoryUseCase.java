package be.kdg.dishsg.pricerange.ports.in;

import be.kdg.dishsg.pricerange.domain.PriceRangePoint;

import java.util.List;
import java.util.UUID;

// In-port for computing a restaurant's price range classification month by month.
public interface GetPriceRangeHistoryUseCase {
    List<PriceRangePoint> getPriceRangeHistory(UUID restaurantId);
}
