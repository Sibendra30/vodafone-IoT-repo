package com.vodafone.iot.error;

public class InvalidStatusException extends RuntimeException{
    public InvalidStatusException(String msg) {
        super(msg);
    }
}
