package com.vodafone.iot.service;

import com.vodafone.iot.model.Device;

import java.util.Set;

public interface DeviceService {
    Set<Device> getDeviceBySim(Set<String> simIds);
}
