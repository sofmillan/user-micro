package com.pragma.powerup.infrastructure.exception;

public class DataAlreadyExistsException extends RuntimeException{

    public DataAlreadyExistsException(){}
    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
