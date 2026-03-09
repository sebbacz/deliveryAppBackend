package be.kdg.dishsg.security.application;

import be.kdg.dishsg.security.application.port.in.SignInOwnerUseCase;
import be.kdg.dishsg.security.application.port.in.SignUpOwnerUseCase;
import be.kdg.dishsg.security.application.exception.EmailAlreadyUsedException;
import be.kdg.dishsg.security.application.exception.InvalidCredentialsException;
import be.kdg.dishsg.security.application.port.out.OwnerAccountRepositoryPort;
import be.kdg.dishsg.security.domain.Owner;
import be.kdg.dishsg.security.domain.OwnerAccount;
import be.kdg.dishsg.security.infra.auth.OwnerSessionTokenStore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
