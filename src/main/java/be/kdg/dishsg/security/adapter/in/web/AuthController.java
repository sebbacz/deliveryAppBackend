package be.kdg.dishsg.security.adapter.in.web;


import be.kdg.dishsg.security.application.port.in.HandleLoginUseCase;
import be.kdg.dishsg.security.domain.Owner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

public class AuthController {

    private final HandleLoginUseCase handleLoginUseCase;

    public AuthController(HandleLoginUseCase handleLoginUseCase) {
        this.handleLoginUseCase = handleLoginUseCase;
    }

    @GetMapping("/me")
    public Owner currentOwner(@AuthenticationPrincipal Jwt jwt) {
        String sub = jwt.getClaimAsString(StandardClaimNames.SUB);
        String email = jwt.getClaimAsString(StandardClaimNames.EMAIL);
        String firstName = jwt.getClaimAsString(StandardClaimNames.GIVEN_NAME);
        String lastName = jwt.getClaimAsString(StandardClaimNames.FAMILY_NAME);

        return handleLoginUseCase.handleLogin(sub, email, firstName, lastName);
    }
}
