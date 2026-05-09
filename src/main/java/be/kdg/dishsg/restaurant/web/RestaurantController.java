package be.kdg.dishsg.restaurant.web;

import be.kdg.dishsg.restaurant.domain.model.Address;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.in.GetMyRestaurantUseCase;
import be.kdg.dishsg.restaurant.web.dto.CreateRestaurantRequest;
import be.kdg.dishsg.restaurant.web.dto.RestaurantResponse;
import be.kdg.dishsg.security.domain.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetMyRestaurantUseCase getMyRestaurantUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase,
                                GetMyRestaurantUseCase getMyRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getMyRestaurantUseCase = getMyRestaurantUseCase;
    }

    // US2: Create restaurant (enforces one per owner)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public RestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest request,
                                               Authentication authentication) {
        String ownerId = extractOwnerId(authentication);
        Restaurant restaurant = new Restaurant(
                null,
                ownerId,
                request.name,
                new Address(request.street, request.number, request.postalCode, request.city, request.country),
                request.contactEmail,
                request.pictureUrl,
                request.defaultPreparationTime,
                request.typeOfCuisine,
                request.openingHours
        );
        return toResponse(createRestaurantUseCase.createRestaurant(restaurant));
    }

    // US2: Check if owner already has a restaurant
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('owner')")
    public RestaurantResponse getMyRestaurant(Authentication authentication) {
        String ownerId = extractOwnerId(authentication);
        return getMyRestaurantUseCase.getRestaurantByOwnerId(ownerId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private String extractOwnerId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Owner owner) {
            return owner.id().toString();
        }
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        throw new IllegalStateException("Unknown principal type");
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
        return dto;
    }
}
