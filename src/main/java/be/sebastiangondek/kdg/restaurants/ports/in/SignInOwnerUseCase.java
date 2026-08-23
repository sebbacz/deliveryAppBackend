package be.sebastiangondek.kdg.restaurants.ports.in;

// In-port for authenticating an owner with email/password and returning a session token.
public interface SignInOwnerUseCase {
    String signIn(String email, String rawPassword);
}
