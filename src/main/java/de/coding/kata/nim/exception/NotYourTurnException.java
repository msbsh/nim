package de.coding.kata.nim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = NotYourTurnException.REASON)
public class NotYourTurnException extends RuntimeException {

    static final String REASON = "It's not the players term to play";

    public NotYourTurnException() {
        super(REASON);
    }
}
