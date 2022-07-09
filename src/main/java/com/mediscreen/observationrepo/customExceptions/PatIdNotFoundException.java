package com.mediscreen.observationrepo.customExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatIdNotFoundException extends Exception{

    public PatIdNotFoundException (String msg){
        super(msg);
    }
}
