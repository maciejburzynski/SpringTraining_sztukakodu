package pl.maltoza.tasks.Boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.maltoza.exceptions.NotFoundException;

import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity notFoundExceptrionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler({IOException.class})
    public ResponseEntity IOExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

}