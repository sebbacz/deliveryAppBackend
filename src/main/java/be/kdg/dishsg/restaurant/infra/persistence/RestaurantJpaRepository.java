package be.kdg.dishsg.restaurant.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, String> {

    List<RestaurantJpaEntity> findByOwnerId(String ownerId);
}