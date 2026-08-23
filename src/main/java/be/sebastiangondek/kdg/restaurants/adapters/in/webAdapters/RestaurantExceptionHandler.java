package be.sebastiangondek.kdg.restaurants.adapters.in.webAdapters;

import be.sebastiangondek.kdg.restaurants.domain.exception.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// gives RestaurantNotFoundException
@RestControllerAdvice
public class RestaurantExceptionHandler {

    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRestaurantNotFound(RestaurantNotFoundException ex) {
        return ex.getMessage();
    }
}
