package be.sebastiangondek.kdg.restaurants.core;

import be.sebastiangondek.kdg.restaurants.adapters.out.auth.OwnerSessionTokenStore;
import be.sebastiangondek.kdg.restaurants.domain.exception.EmailAlreadyUsedException;
import be.sebastiangondek.kdg.restaurants.domain.exception.InvalidCredentialsException;
import be.sebastiangondek.kdg.restaurants.domain.Owner;
import be.sebastiangondek.kdg.restaurants.domain.OwnerAccount;
import be.sebastiangondek.kdg.restaurants.ports.in.SignInOwnerUseCase;
import be.sebastiangondek.kdg.restaurants.ports.in.SignUpOwnerUseCase;
import be.sebastiangondek.kdg.restaurants.ports.out.OwnerAccountRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

// Handles sign-up and sign-in for the custom session-based auth path NOT KEYCLOAK
@Service
public class OwnerAuthenticationService implements SignUpOwnerUseCase, SignInOwnerUseCase {

    private final OwnerAccountRepositoryPort ownerAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final OwnerSessionTokenStore tokenStore;

    public OwnerAuthenticationService(OwnerAccountRepositoryPort ownerAccountRepository,
                                      PasswordEncoder passwordEncoder,
                                      OwnerSessionTokenStore tokenStore) {
        this.ownerAccountRepository = ownerAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
    }

    @Override
    public Owner signUp(String email, String rawPassword, String firstName, String lastName) {
        ownerAccountRepository.findByEmail(email).ifPresent(existing -> {
            throw new EmailAlreadyUsedException();
        });

        OwnerAccount toSave = new OwnerAccount(
                UUID.randomUUID(),
                email,
                passwordEncoder.encode(rawPassword),
                firstName,
                lastName
        );

        OwnerAccount saved = ownerAccountRepository.save(toSave);
        return new Owner(saved.id(), saved.email(), saved.firstName(), saved.lastName());
    }

    @Override
    public String signIn(String email, String rawPassword) {
        OwnerAccount ownerAccount = ownerAccountRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(rawPassword, ownerAccount.passwordHash())) {
            throw new InvalidCredentialsException();
        }

        Owner owner = new Owner(ownerAccount.id(), ownerAccount.email(), ownerAccount.firstName(), ownerAccount.lastName());
        return tokenStore.issueToken(owner);
    }
}
