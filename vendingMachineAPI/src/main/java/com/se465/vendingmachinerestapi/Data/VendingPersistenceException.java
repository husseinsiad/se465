package com.se465.vendingmachinerestapi.Data;

public class VendingPersistenceException extends Exception{
    public VendingPersistenceException(String message) {
        super(message);
    }

    public VendingPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
