package com.shopsy.ecom_api.Exception;

//i choose runtimeexception instead of exception  class because we need not have to add throws signature in the method
public class ProductException extends RuntimeException{
    
    public ProductException(String message) {
        super(message);
    }
    
}
