package com.scaler.user_service.exceptions;

public class TokenNotExistOrAlreadyExpired extends Exception {
    public TokenNotExistOrAlreadyExpired(String message) {
        super(message);
    }
}
