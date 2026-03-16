package com.pao.laboratory03.bonus.exception;

import com.pao.laboratory03.bonus.model.Status;

public class InvalidTransitionException extends RuntimeException {
    private final Status from;
    private final Status to;

    public InvalidTransitionException(Status from, Status to) {
        super("Tranzitie invalida de la " + from + " la " + to);
        this.from = from;
        this.to = to;
    }
}