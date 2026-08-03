package be.kdg.dishsg.restaurant.adapters.out.jpaRepositories.restaurantRepo;

import be.kdg.dishsg.restaurant.adapters.out.jpaRepositories.restaurantRepo.entities.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA repository for restaurants with owner-scoped queries and delete.
public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, String> {

    List<RestaurantJpaEntity> findByOwnerId(String ownerId);
    void deleteByOwnerId(String ownerId);
}
