package com.example.spacecatsmarket.service.exception;

public class PersistenceException extends RuntimeException{

    public PersistenceException(Throwable ex){
        super(ex);
    }
}
