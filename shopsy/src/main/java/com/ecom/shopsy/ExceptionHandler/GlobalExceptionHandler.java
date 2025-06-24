package com.ecom.shopsy.ExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public String DataIntegrity(DataIntegrityViolationException di)
    {
        String s=di.getMessage();
        
        System.out.println(s);
        return s;
    }
    
     @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String DataMissing(MethodArgumentNotValidException vi)
    {
        String s=vi.getFieldError().getDefaultMessage();
        
        System.out.println(s);
        return s;
    }
    @ExceptionHandler(CustomerOrderException.class)
    @ResponseBody
    public String CustomerOrderException(CustomerOrderException coe)
    {
        return coe.getMessage();
    }
}
