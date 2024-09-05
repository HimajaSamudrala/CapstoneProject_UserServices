package com.scaler.user_service.exceptions;


public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message)
    {
        super(message);
    }
}
