package com.vodafone.iot.error;

public class DeviceNotFoundException extends RuntimeException{
    public DeviceNotFoundException(String msg) {
        super(msg);
    }
}
