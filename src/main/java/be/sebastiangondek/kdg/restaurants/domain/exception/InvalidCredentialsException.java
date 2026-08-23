package be.sebastiangondek.kdg.restaurants.domain.exception;

// Thrown when the provided email/password combination does not match any stored account.
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials.");
    }
}
