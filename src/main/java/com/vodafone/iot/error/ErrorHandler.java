package com.vodafone.iot.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = {DeviceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> resourceNotFoundExceptionHandler(DeviceNotFoundException ex) {
        return new HashMap(){{
            put("error", "invalidDeviceId");
            put("message", ex.getMessage());
        }};
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> invalidArgsHandler(Exception ex) {
        return new HashMap(){{
            put("error", "badInputValues");
            put("message", "Please verify request parameters");
        }};
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> genericExceptionHandler(Exception ex) {
        return new HashMap(){{
            put("error", "internalServerError");
            put("message", "Something went wrong. Please contact system admin");
        }};
    }

    @ExceptionHandler(value = {InvalidStatusException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> genericExceptionHandler(InvalidStatusException ex) {
        return new HashMap(){{
            put("error", "invalidStatusValue");
            put("message", ex.getMessage());
        }};
    }
}
