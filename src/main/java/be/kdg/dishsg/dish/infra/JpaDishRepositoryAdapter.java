package be.kdg.dishsg.dish.infra;

import be.kdg.dishsg.dish.domain.Dish;
import be.kdg.dishsg.dish.repository.DishRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class JpaDishRepositoryAdapter  implements DishRepositoryPort {

    private final SpringDataDishRepository springRepo;

    public JpaDishRepositoryAdapter(SpringDataDishRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public List<Dish> findAllDraftsByRestaurant(UUID restaurantId) {
        return springRepo.findByRestaurantIdAndDraftTrue(restaurantId);
    }

    @Override
    public void saveAll(List<Dish> dishes) {
        springRepo.saveAll(dishes);
    }
}
