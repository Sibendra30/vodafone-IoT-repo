package com.vodafone.iot.controller;

import com.vodafone.iot.api.DeviceApi;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.service.DeviceService;
import com.vodafone.iot.service.SimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class DeviceController implements DeviceApi {

    private final DeviceService deviceService;
    private final SimService simService;

    public DeviceController(DeviceService deviceService, SimService simService) {
        this.deviceService = deviceService;
        this.simService = simService;
    }

    @Override
    public ResponseEntity<Set<Device>> getDevice(String simStatus) {
        Set<Sim> simList = this.simService.getSimByStatus(simStatus);
        Set<String> simIdList = simList.stream().map(sim -> sim.getSimId()).collect(Collectors.toSet());
        Set<Device> devices = this.deviceService.getDeviceBySim(simIdList);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Device> updateDeviceStatus(String deviceId, Device device) {
        return null;
    }

    @Override
    public void deleteDevice(String deviceId) {

    }
}
