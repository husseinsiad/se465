package com.se465.vendingmachinerestapi.Service;

public class InsufficientFundsException  extends Exception{
    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
