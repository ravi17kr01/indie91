package com.example.indie91.Exceptions;

public class NoUsersFoundException extends RuntimeException {
    public NoUsersFoundException(String message){
        super(message);
    }
}
