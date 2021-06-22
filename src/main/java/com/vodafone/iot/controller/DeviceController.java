package com.vodafone.iot.controller;

import com.vodafone.iot.api.DeviceApi;
import com.vodafone.iot.error.InvalidStatusException;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.PaginatedResponse;
import com.vodafone.iot.model.PaginationMetadata;
import com.vodafone.iot.service.DeviceService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController implements DeviceApi {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public ResponseEntity<PaginatedResponse<Device>> getDevice(String simStatus, int pageSize, int pageNumber) {
        Page<Device> devices = this.deviceService.getDeviceBySimStatus(simStatus, pageSize, pageNumber);
        return new ResponseEntity<>(new PaginatedResponse<>(devices.toSet(),
                new PaginationMetadata(pageSize, pageNumber, devices.getTotalElements(), devices.getTotalPages())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Device> updateDeviceStatus(String deviceId, Device device) {
        if ("READY".equals(device.getStatus()) || "NOT_READY".equals(device.getStatus())) {
            Device updatedDevice = this.deviceService.updateDeviceConfigurationStatus(device.getStatus(), deviceId);
            return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
        }
       throw new InvalidStatusException("Invalid status. Valid values for status are - READY/NOT_READY");
    }

    @Override
    public ResponseEntity deleteDevice(String deviceId) {
        this.deviceService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
    }
}
