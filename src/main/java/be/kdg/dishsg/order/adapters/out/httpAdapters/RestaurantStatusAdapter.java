package be.kdg.dishsg.order.adapters.out.httpAdapters;

import be.kdg.dishsg.order.ports.out.RestaurantStatusPort;
import be.kdg.dishsg.restaurant.ports.in.GetRestaurantByIdUseCase;
import org.springframework.stereotype.Component;

import java.util.UUID;

// Bridges the order module to the restaurant module to check open status before accepting orders.
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
