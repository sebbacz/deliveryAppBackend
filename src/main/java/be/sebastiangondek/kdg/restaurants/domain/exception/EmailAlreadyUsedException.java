package be.sebastiangondek.kdg.restaurants.domain.exception;

// Thrown when a sign-up is attempted with an email address already registered in the system.
public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Email is already in use.");
    }
}
