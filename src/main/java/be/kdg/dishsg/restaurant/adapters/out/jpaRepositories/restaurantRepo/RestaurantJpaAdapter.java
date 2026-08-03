package be.kdg.dishsg.restaurant.adapters.out.jpaRepositories.restaurantRepo;

import be.kdg.dishsg.restaurant.adapters.out.jpaRepositories.restaurantRepo.entities.AddressEmbeddable;
import be.kdg.dishsg.restaurant.adapters.out.jpaRepositories.restaurantRepo.entities.RestaurantJpaEntity;
import be.kdg.dishsg.restaurant.domain.model.Address;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestaurantJpaAdapter implements RestaurantRepositoryPort {

    private final RestaurantJpaRepository repository;

    public RestaurantJpaAdapter(RestaurantJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        AddressEmbeddable address = new AddressEmbeddable(
                restaurant.getAddress().getStreet(),
                restaurant.getAddress().getNumber(),
                restaurant.getAddress().getPostalCode(),
                restaurant.getAddress().getCity(),
                restaurant.getAddress().getCountry()
        );

        RestaurantJpaEntity entity = new RestaurantJpaEntity(
                restaurant.getId(),
                restaurant.getOwnerId(),
                restaurant.getName(),
                address,
                restaurant.getContactEmail(),
                restaurant.getPictureUrls(),
                restaurant.getDefaultPreparationTime(),
                restaurant.getTypeOfCuisine(),
                restaurant.getOpeningHours(),
                restaurant.isOpen(),
                restaurant.isManualOverride(),
                restaurant.getLatitude(),
                restaurant.getLongitude()
        );

        repository.save(entity);
        return restaurant;
    }

    @Override
    public Optional<Restaurant> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByOwnerId(String ownerId) {
        repository.deleteByOwnerId(ownerId);
    }

    @Override
    public List<Restaurant> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Restaurant toDomain(RestaurantJpaEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                new Address(
                        entity.getAddress().getStreet(),
                        entity.getAddress().getNumber(),
                        entity.getAddress().getPostalCode(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getCountry()
                ),
                entity.getContactEmail(),
                entity.getPictureUrls(),
                entity.getDefaultPreparationTime(),
                entity.getTypeOfCuisine(),
                entity.getOpeningHours(),
                entity.isOpen(),
                entity.isManualOverride(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }
}
