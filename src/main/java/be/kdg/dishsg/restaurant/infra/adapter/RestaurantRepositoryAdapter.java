package be.kdg.dishsg.restaurant.infra.adapter;

import be.kdg.dishsg.restaurant.domain.model.Address;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.infra.persistence.AddressEmbeddable;
import be.kdg.dishsg.restaurant.infra.persistence.RestaurantJpaEntity;
import be.kdg.dishsg.restaurant.infra.persistence.RestaurantJpaRepository;
import be.kdg.dishsg.restaurant.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class RestaurantRepositoryAdapter  implements RestaurantRepositoryPort {


    private final RestaurantJpaRepository repository;

    public RestaurantRepositoryAdapter(RestaurantJpaRepository repository) {
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
                restaurant.getPictureUrl(),
                restaurant.getDefaultPreparationTime(),
                restaurant.getTypeOfCuisine(),
                restaurant.getOpeningHours(),
                restaurant.isOpen(),
                restaurant.getLatitude(),
                restaurant.getLongitude()
        );

        repository.save(entity);
        return restaurant;
    }

    @Override
    public Optional<Restaurant> findById(String id) {
        return repository.findById(id)
                .map(entity -> new Restaurant(
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
                        entity.getPictureUrl(),
                        entity.getDefaultPreparationTime(),
                        entity.getTypeOfCuisine(),
                        entity.getOpeningHours(),
                        entity.isOpen(),
                        entity.getLatitude(),
                        entity.getLongitude()
                ));
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Restaurant(
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
                        entity.getPictureUrl(),
                        entity.getDefaultPreparationTime(),
                        entity.getTypeOfCuisine(),
                        entity.getOpeningHours(),
                        entity.isOpen(),
                        entity.getLatitude(),
                        entity.getLongitude()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> findByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId).stream()
                .map(entity -> new Restaurant(
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
                        entity.getPictureUrl(),
                        entity.getDefaultPreparationTime(),
                        entity.getTypeOfCuisine(),
                        entity.getOpeningHours(),
                        entity.isOpen(),
                        entity.getLatitude(),
                        entity.getLongitude()
                ))
                .collect(Collectors.toList());
    }

}
