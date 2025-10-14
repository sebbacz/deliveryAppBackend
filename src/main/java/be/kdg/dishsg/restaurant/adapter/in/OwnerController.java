package be.kdg.dishsg.restaurant.adapter.in;

import be.kdg.dishsg.restaurant.domain.Owner;
import be.kdg.dishsg.restaurant.port.in.GetOrCreateOwnerProfileUseCase;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final GetOrCreateOwnerProfileUseCase getOrCreateOwnerProfileUseCase;

    public OwnerController(GetOrCreateOwnerProfileUseCase getOrCreateOwnerProfileUseCase) {
        this.getOrCreateOwnerProfileUseCase = getOrCreateOwnerProfileUseCase;
    }

    @GetMapping("/me")
    public GetOrCreateOwnerProfileUseCase.OwnerProfile getProfile(@AuthenticationPrincipal Jwt jwt) {
        UUID id = UUID.fromString(jwt.getClaimAsString(StandardClaimNames.SUB));
        String email = jwt.getClaimAsString(StandardClaimNames.EMAIL);
        String firstName = jwt.getClaimAsString(StandardClaimNames.GIVEN_NAME);
        String lastName = jwt.getClaimAsString(StandardClaimNames.FAMILY_NAME);

        Owner owner = new Owner(id, email, firstName, lastName);
        return getOrCreateOwnerProfileUseCase.getOrCreate(owner);
    }
}
