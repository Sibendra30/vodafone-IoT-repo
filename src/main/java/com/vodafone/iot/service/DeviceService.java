package com.vodafone.iot.service;

import com.vodafone.iot.model.Device;
import org.springframework.data.domain.Page;


public interface DeviceService {
    Page<Device> getDeviceBySimStatus(String simStatus, int pageSize, int pageNumber);
    Device updateDeviceConfigurationStatus(String deviceConfigStatus, String deviceId);
    void deleteDevice(String deviceId);
}
