package be.kdg.dishsg.security.application;

import be.kdg.dishsg.security.application.exception.EmailAlreadyUsedException;
import be.kdg.dishsg.security.application.exception.InvalidCredentialsException;
import be.kdg.dishsg.security.application.port.out.OwnerAccountRepositoryPort;
import be.kdg.dishsg.security.domain.Owner;
import be.kdg.dishsg.security.domain.OwnerAccount;
import be.kdg.dishsg.security.infra.auth.OwnerSessionTokenStore;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OwnerAuthenticationServiceTest {

    @Test
    void signUpCreatesOwnerAccount() {
        InMemoryOwnerRepo repo = new InMemoryOwnerRepo();
        OwnerAuthenticationService service = new OwnerAuthenticationService(repo, new BCryptPasswordEncoder(), new OwnerSessionTokenStore());

        Owner owner = service.signUp("owner@kdg.be", "Password123", "Ada", "Lovelace");

        assertNotNull(owner.id());
        assertEquals("owner@kdg.be", owner.email());
    }

    @Test
    void signUpRejectsDuplicateEmail() {
        InMemoryOwnerRepo repo = new InMemoryOwnerRepo();
        OwnerAuthenticationService service = new OwnerAuthenticationService(repo, new BCryptPasswordEncoder(), new OwnerSessionTokenStore());
        service.signUp("owner@kdg.be", "Password123", "Ada", "Lovelace");

        assertThrows(EmailAlreadyUsedException.class,
                () -> service.signUp("owner@kdg.be", "Password123", "Alan", "Turing"));
    }

    @Test
    void signInReturnsTokenForValidCredentials() {
        InMemoryOwnerRepo repo = new InMemoryOwnerRepo();
        OwnerAuthenticationService service = new OwnerAuthenticationService(repo, new BCryptPasswordEncoder(), new OwnerSessionTokenStore());
        service.signUp("owner@kdg.be", "Password123", "Ada", "Lovelace");

        String token = service.signIn("owner@kdg.be", "Password123");

        assertNotNull(token);
    }

    @Test
    void signInRejectsInvalidPassword() {
        InMemoryOwnerRepo repo = new InMemoryOwnerRepo();
        OwnerAuthenticationService service = new OwnerAuthenticationService(repo, new BCryptPasswordEncoder(), new OwnerSessionTokenStore());
        service.signUp("owner@kdg.be", "Password123", "Ada", "Lovelace");

        assertThrows(InvalidCredentialsException.class, () -> service.signIn("owner@kdg.be", "wrong"));
    }

    private static final class InMemoryOwnerRepo implements OwnerAccountRepositoryPort {
        private final Map<UUID, OwnerAccount> data = new HashMap<>();

        @Override
        public OwnerAccount save(OwnerAccount ownerAccount) {
            data.put(ownerAccount.id(), ownerAccount);
            return ownerAccount;
        }

        @Override
        public Optional<OwnerAccount> findByEmail(String email) {
            return data.values().stream().filter(o -> o.email().equals(email)).findFirst();
        }

        @Override
        public Optional<OwnerAccount> findById(UUID ownerId) {
            return Optional.ofNullable(data.get(ownerId));
        }
    }
}
