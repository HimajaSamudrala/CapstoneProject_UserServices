package com.scaler.user_service.exceptions;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String message)
    {
        super(message);
    }
}
