package de.coding.kata.nim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = GameNotFoundException.REASON)
public class GameNotFoundException extends RuntimeException {

    static final String REASON = "Game cannot be found by id";

    public GameNotFoundException() {
        super(REASON);
    }
}
