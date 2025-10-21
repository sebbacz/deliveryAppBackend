package be.kdg.dishsg.restaurant.web;


import be.kdg.dishsg.restaurant.domain.model.Address;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantUseCase;
import be.kdg.dishsg.restaurant.web.dto.CreateRestaurantRequest;
import be.kdg.dishsg.restaurant.web.dto.RestaurantResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('owner')")
    public RestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest request, @AuthenticationPrincipal Jwt token) {
        Address address = new Address(
                request.street,
                request.number,
                request.postalCode,
                request.city,
                request.country
        );

        String ownerId = token.getSubject();

        Restaurant restaurant = new Restaurant(
                null,
                ownerId,
                request.name,
                address,
                request.contactEmail,
                request.pictureUrl,
                request.defaultPreparationTime,
                request.typeOfCuisine,
                request.openingHours
        );

        Restaurant created = createRestaurantUseCase.createRestaurant(restaurant);

        RestaurantResponse response = new RestaurantResponse();
        response.id = created.getId();
        response.name = created.getName();
        response.contactEmail = created.getContactEmail();
        response.typeOfCuisine = created.getTypeOfCuisine();
        response.openingHours = created.getOpeningHours();

        return response;
    }
}
