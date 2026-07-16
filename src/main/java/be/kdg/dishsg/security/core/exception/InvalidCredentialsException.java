package be.kdg.dishsg.security.core.exception;

// Thrown when the provided email/password combination does not match any stored account.
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials.");
    }
}
