package be.sebastiangondek.kdg.orders.adapters.out.httpAdapters;

import be.sebastiangondek.kdg.orders.ports.out.RestaurantStatusPort;
import be.sebastiangondek.kdg.restaurants.ports.in.GetRestaurantByIdUseCase;
import org.springframework.stereotype.Component;

import java.util.UUID;

// orders module calls restaurant module to check open status before accepting a new order.
@Component
public class RestaurantStatusAdapter implements RestaurantStatusPort {

    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;

    public RestaurantStatusAdapter(GetRestaurantByIdUseCase getRestaurantByIdUseCase) {
        this.getRestaurantByIdUseCase = getRestaurantByIdUseCase;
    }

    @Override
    public boolean isRestaurantOpen(UUID restaurantId) {
        return getRestaurantByIdUseCase.getRestaurantById(restaurantId.toString())
                .map(r -> r.isOpen())
                .orElse(false);
    }
}
