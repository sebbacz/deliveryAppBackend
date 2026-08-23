package be.sebastiangondek.kdg.restaurants.ports.out;

//   interface combining the focused Load and Save ports.
public interface RestaurantRepositoryPort extends LoadRestaurantPort, SaveRestaurantPort {
    void deleteByOwnerId(String ownerId);
}
