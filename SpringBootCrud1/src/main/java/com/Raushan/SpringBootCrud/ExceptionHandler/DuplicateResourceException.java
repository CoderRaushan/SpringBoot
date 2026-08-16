package com.Raushan.SpringBootCrud.ExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class DuplicateResourceException extends RuntimeException{
    public  DuplicateResourceException(String message)
    {
        super(message);
    }
}
