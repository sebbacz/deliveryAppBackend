package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters;

import be.sebastiangondek.kdg.restaurants.adapters.in.dto.RestaurantResponse;
import be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.requests.CreateRestaurantRequest;
import be.sebastiangondek.kdg.restaurants.domain.Restaurant;
import be.sebastiangondek.kdg.restaurants.ports.in.CreateRestaurantCmd;
import be.sebastiangondek.kdg.restaurants.ports.in.CreateRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.DeleteRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.GetMyRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.OpenCloseRestaurantUseCase;
import be.sebastiangondek.kdg.restaurants.domain.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// owner restaurant management: create, open/close, and delete.
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
                                               @AuthenticationPrincipal Object principal) {
        String ownerId = extractOwnerId(principal);
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
    public ResponseEntity<RestaurantResponse> getMyRestaurant(@AuthenticationPrincipal Object principal) {
        String ownerId = extractOwnerId(principal);
        return getMyRestaurantUseCase.getRestaurantByOwnerId(ownerId)
                .map(r -> ResponseEntity.ok(toResponse(r)))
                .orElse(ResponseEntity.noContent().build());
    }

    @PutMapping("/my/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void openRestaurant(@AuthenticationPrincipal Object principal) {
        openCloseRestaurantUseCase.openRestaurant(extractOwnerId(principal));
    }

    @PutMapping("/my/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void closeRestaurant(@AuthenticationPrincipal Object principal) {
        openCloseRestaurantUseCase.closeRestaurant(extractOwnerId(principal));
    }

    @DeleteMapping("/my")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('owner')")
    public void deleteRestaurant(@AuthenticationPrincipal Object principal) {
        deleteRestaurantUseCase.deleteRestaurantByOwnerId(extractOwnerId(principal));
    }

    private String extractOwnerId(Object principal) {
        return switch (principal) {
            case Owner owner -> owner.id().toString();
            case Jwt jwt -> jwt.getSubject();
            default -> throw new IllegalStateException("Unknown principal type: " + principal.getClass());
        };
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
