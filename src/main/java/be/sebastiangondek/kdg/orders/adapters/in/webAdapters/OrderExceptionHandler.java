package be.sebastiangondek.kdg.orders.adapters.in.webAdapters;

import be.sebastiangondek.kdg.orders.domain.exception.OrderNotFoundException;
import be.sebastiangondek.kdg.orders.domain.exception.RestaurantClosedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Maps order domain exceptions: OrderNotFoundException and RestaurantClosedException
@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOrderNotFound(OrderNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RestaurantClosedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRestaurantClosed(RestaurantClosedException ex) {
        return ex.getMessage();
    }
}
