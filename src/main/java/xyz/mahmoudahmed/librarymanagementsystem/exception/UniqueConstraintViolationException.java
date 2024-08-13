package xyz.mahmoudahmed.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(final String message) {
        super(message);
    }
}
