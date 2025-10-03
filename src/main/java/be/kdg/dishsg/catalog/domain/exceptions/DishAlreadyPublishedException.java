package be.kdg.dishsg.catalog.domain.exceptions;

public class DishAlreadyPublishedException extends RuntimeException {

    public DishAlreadyPublishedException() {
        super("Dish is already published.");
    }

    public DishAlreadyPublishedException(String message) {
        super(message);
    }
}
