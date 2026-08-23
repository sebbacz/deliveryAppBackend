package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters;

import be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.dto.AuthTokenResponse;
import be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.dto.SignInRequest;
import be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters.dto.SignUpRequest;
import be.sebastiangondek.kdg.restaurants.domain.Owner;
import be.sebastiangondek.kdg.restaurants.ports.in.HandleLoginUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.SignInOwnerUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.SignUpOwnerUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// REST controller for custom sign in/ sign up and  JWT owner identity.
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
    public Owner currentOwner(@AuthenticationPrincipal Object principal) {
        return switch (principal) {
            case Owner owner -> owner;
            case Jwt jwt -> handleLoginUseCase.handleLogin(
                    jwt.getClaimAsString(StandardClaimNames.SUB), //Subject ->> owners of kc user id
                    jwt.getClaimAsString(StandardClaimNames.EMAIL),
                    jwt.getClaimAsString(StandardClaimNames.GIVEN_NAME),
                    jwt.getClaimAsString(StandardClaimNames.FAMILY_NAME));
            default -> throw new IllegalStateException("Unknown principal type: " + principal.getClass());
        };
    }
}
