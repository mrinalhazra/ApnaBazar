package com.app.apnabazar.exception;

public class AlreadyExists extends RuntimeException {
    public AlreadyExists() {
    }

    public AlreadyExists(String message) {
        super(message);
    }

    public AlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
