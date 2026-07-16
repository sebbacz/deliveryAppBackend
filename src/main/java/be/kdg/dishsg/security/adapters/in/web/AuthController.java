package be.kdg.dishsg.security.adapters.in.web;

import be.kdg.dishsg.security.adapters.in.web.dto.AuthTokenResponse;
import be.kdg.dishsg.security.adapters.in.web.dto.SignInRequest;
import be.kdg.dishsg.security.adapters.in.web.dto.SignUpRequest;
import be.kdg.dishsg.security.domain.Owner;
import be.kdg.dishsg.security.ports.in.HandleLoginUseCase;
import be.kdg.dishsg.security.ports.in.SignInOwnerUseCase;
import be.kdg.dishsg.security.ports.in.SignUpOwnerUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// REST controller for custom session auth (sign-up/sign-in) and Keycloak JWT owner identity resolution.
@RestController
public class AuthController {

    private final HandleLoginUseCase handleLoginUseCase;
    private final SignUpOwnerUseCase signUpOwnerUseCase;
    private final SignInOwnerUseCase signInOwnerUseCase;

    public AuthController(HandleLoginUseCase handleLoginUseCase,
                          SignUpOwnerUseCase signUpOwnerUseCase,
                          SignInOwnerUseCase signInOwnerUseCase) {
        this.handleLoginUseCase = handleLoginUseCase;
        this.signUpOwnerUseCase = signUpOwnerUseCase;
        this.signInOwnerUseCase = signInOwnerUseCase;
    }

    @PostMapping("/unsecured/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner signUp(@Valid @RequestBody SignUpRequest request) {
        return signUpOwnerUseCase.signUp(request.email, request.password, request.firstName, request.lastName);
    }

    @PostMapping("/unsecured/auth/signin")
    public AuthTokenResponse signIn(@Valid @RequestBody SignInRequest request) {
        String token = signInOwnerUseCase.signIn(request.email, request.password);

        AuthTokenResponse response = new AuthTokenResponse();
        response.tokenType = "Bearer";
        response.accessToken = token;
        return response;
    }

    @GetMapping("/api/auth/me")
    public Owner currentOwner(Authentication authentication, @AuthenticationPrincipal Jwt jwt) {
        if (authentication != null && authentication.getPrincipal() instanceof Owner owner) {
            return owner;
        }

        String sub = jwt.getClaimAsString(StandardClaimNames.SUB);
        String email = jwt.getClaimAsString(StandardClaimNames.EMAIL);
        String firstName = jwt.getClaimAsString(StandardClaimNames.GIVEN_NAME);
        String lastName = jwt.getClaimAsString(StandardClaimNames.FAMILY_NAME);

        return handleLoginUseCase.handleLogin(sub, email, firstName, lastName);
    }
}
