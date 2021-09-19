package com.superapp.firstdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class Apiunauthorized extends Exception {

    public Apiunauthorized(String message) {
        super(message);
    }
}