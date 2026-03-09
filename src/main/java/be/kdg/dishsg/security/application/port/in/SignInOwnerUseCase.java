package be.kdg.dishsg.security.application.port.in;

public interface SignInOwnerUseCase {
    String signIn(String email, String rawPassword);
}
