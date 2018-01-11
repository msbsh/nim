package de.coding.kata.nim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to take more matches than available")
public class InvalidMatchCountException extends RuntimeException {

    public InvalidMatchCountException(String message) {
        super(message);
    }
}
