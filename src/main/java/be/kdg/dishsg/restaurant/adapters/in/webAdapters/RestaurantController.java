package be.kdg.dishsg.restaurant.adapters.in.webAdapters;

import be.kdg.dishsg.restaurant.adapters.in.dto.RestaurantResponse;
import be.kdg.dishsg.restaurant.adapters.in.webAdapters.requests.CreateRestaurantRequest;
import be.kdg.dishsg.restaurant.domain.model.Restaurant;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantCmd;
import be.kdg.dishsg.restaurant.ports.in.CreateRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.in.DeleteRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.in.GetMyRestaurantUseCase;
import be.kdg.dishsg.restaurant.ports.in.OpenCloseRestaurantUseCase;
import be.kdg.dishsg.security.domain.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final OpenCloseRestaurantUseCase openCloseRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase,
                                GetMyRestaurantUseCase getMyRestaurantUseCase,
                                OpenCloseRestaurantUseCase openCloseRestaurantUseCase,
                                DeleteRestaurantUseCase deleteRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getMyRestaurantUseCase = getMyRestaurantUseCase;
        this.openCloseRestaurantUseCase = openCloseRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('owner')")
    public RestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest request,
                                               Authentication authentication) {
        String ownerId = extractOwnerId(authentication);
        CreateRestaurantCmd cmd = new CreateRestaurantCmd(
                ownerId,
                request.name,
                request.street,
                request.number,
                request.postalCode,
                request.city,
                request.country,
                request.contactEmail,
                request.pictureUrls,
                request.defaultPreparationTime,
                request.typeOfCuisine,
                request.openingHours
        );
        try {
            return toResponse(createRestaurantUseCase.createRestaurant(cmd));
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<RestaurantResponse> getMyRestaurant(Authentication authentication) {
        String ownerId = extractOwnerId(authentication);
        return getMyRestaurantUseCase.getRestaurantByOwnerId(ownerId)
                .map(r -> ResponseEntity.ok(toResponse(r)))
                .orElse(ResponseEntity.noContent().build());
    }

    @PutMapping("/my/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void openRestaurant(Authentication authentication) {
        openCloseRestaurantUseCase.openRestaurant(extractOwnerId(authentication));
    }

    @PutMapping("/my/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void closeRestaurant(Authentication authentication) {
        openCloseRestaurantUseCase.closeRestaurant(extractOwnerId(authentication));
    }

    @DeleteMapping("/my")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void deleteRestaurant(Authentication authentication) {
        deleteRestaurantUseCase.deleteRestaurantByOwnerId(extractOwnerId(authentication));
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
        dto.pictureUrls = r.getPictureUrls();
        dto.defaultPreparationTime = r.getDefaultPreparationTime();
        dto.typeOfCuisine = r.getTypeOfCuisine();
        dto.openingHours = r.getOpeningHours();
        dto.isOpen = r.isOpen();
        dto.latitude = r.getLatitude();
        dto.longitude = r.getLongitude();
        return dto;
    }
}
