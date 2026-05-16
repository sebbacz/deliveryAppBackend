package be.kdg.dishsg.pricerange.ports.in;

import be.kdg.dishsg.pricerange.domain.PriceRangePoint;

import java.util.List;
import java.util.UUID;

public interface GetPriceRangeHistoryUseCase {
    List<PriceRangePoint> getPriceRangeHistory(UUID restaurantId);
}
