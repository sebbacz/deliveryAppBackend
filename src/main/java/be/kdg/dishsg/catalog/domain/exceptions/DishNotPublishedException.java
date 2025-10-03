package be.kdg.dishsg.catalog.domain.exceptions;

public class DishNotPublishedException extends RuntimeException {
    public DishNotPublishedException() {
        super("Dish is not published, cannot be unpublished.");
    }
}