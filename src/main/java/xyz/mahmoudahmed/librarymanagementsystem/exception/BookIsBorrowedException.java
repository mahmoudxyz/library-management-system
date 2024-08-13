package xyz.mahmoudahmed.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookIsBorrowedException extends RuntimeException {
    public BookIsBorrowedException() {
        super();
    }

    public BookIsBorrowedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BookIsBorrowedException(final String message) {
        super(message);
    }

    public BookIsBorrowedException(final Throwable cause) {
        super(cause);
    }
}
