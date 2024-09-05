package com.scaler.user_service.exceptions;

public class PasswordNotMatchesException extends Exception {
    public PasswordNotMatchesException(String message) {
        super(message);
    }
}
