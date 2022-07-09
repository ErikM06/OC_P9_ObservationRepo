package com.mediscreen.observationrepo.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus (HttpStatus.NOT_FOUND)
public class IdNotFoundException extends Exception{

    public IdNotFoundException(String msg){
        super(msg);
    }
}
