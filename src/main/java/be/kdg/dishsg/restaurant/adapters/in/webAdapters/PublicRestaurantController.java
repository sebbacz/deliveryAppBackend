package be.kdg.dishsg.restaurant.adapters.in.webAdapters;

import be.kdg.dishsg.restaurant.adapters.in.dto.RestaurantResponse;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.GetAllRestaurantsUseCase;
import be.kdg.dishsg.restaurant.ports.in.GetRestaurantByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

// Public REST controller exposing the restaurant listing and single-restaurant lookup without authentication.
@RestController
@RequestMapping("/unsecured/restaurants")
public class PublicRestaurantController {

    private final GetAllRestaurantsUseCase getAllRestaurantsUseCase;
    private final GetRestaurantByIdUseCase getRestaurantByIdUseCase;

    public PublicRestaurantController(GetAllRestaurantsUseCase getAllRestaurantsUseCase,
                                      GetRestaurantByIdUseCase getRestaurantByIdUseCase) {
        this.getAllRestaurantsUseCase = getAllRestaurantsUseCase;
        this.getRestaurantByIdUseCase = getRestaurantByIdUseCase;
    }

    @GetMapping
    public List<RestaurantResponse> getAllRestaurants() {
        return getAllRestaurantsUseCase.getAllRestaurants().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RestaurantResponse getRestaurant(@PathVariable String id) {
        return getRestaurantByIdUseCase.getRestaurantById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private RestaurantResponse toResponse(Restaurant r) {
        RestaurantResponse dto = new RestaurantResponse();
        dto.id = r.getId();
        dto.name = r.getName();
        dto.street = r.getAddress().getStreet();
        dto.number = r.getAddress().getNumber();
        dto.postalCode = r.getAddress().getPostalCode();
        dto.city = r.getAddress().getCity();
        dto.country = r.getAddress().getCountry();
        dto.contactEmail = r.getContactEmail();
        dto.pictureUrl = r.getPictureUrl();
        dto.defaultPreparationTime = r.getDefaultPreparationTime();
        dto.typeOfCuisine = r.getTypeOfCuisine();
        dto.openingHours = r.getOpeningHours();
        dto.isOpen = r.isOpen();
        dto.latitude = r.getLatitude();
        dto.longitude = r.getLongitude();
        return dto;
    }
}
