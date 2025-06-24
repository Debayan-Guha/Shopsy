package com.ecom.shopsy.ExceptionHandler;

public class CustomerOrderException extends RuntimeException{
    
    public CustomerOrderException(String msg)
    {
        super(msg);
    }
}
