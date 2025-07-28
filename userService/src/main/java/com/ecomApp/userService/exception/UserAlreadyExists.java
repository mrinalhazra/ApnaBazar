package com.ecomApp.userService.exception;

public class UserAlreadyExists extends RuntimeException{

    public UserAlreadyExists(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
