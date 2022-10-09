package com.example.instapic.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhotoNotFoundExeption extends  RuntimeException {
    public PhotoNotFoundExeption(String msg) {
        super(msg);

    }
}