package com.vodafone.iot.service;

import com.vodafone.iot.error.DeviceNotFoundException;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.repository.DeviceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Page<Device> getDeviceBySimStatus(String simStatus, int pageSize, int pageNumber) {
        return this.deviceRepository.findDeviceByStatusAndSim_StatusOrderByCreatedDateDesc(
                "READY", simStatus, PageRequest.of(pageNumber - 1, pageSize));
    }

    @Override
    public void deleteDevice(String deviceId) {
        Optional<Device> opDevice = this.deviceRepository.findById(deviceId);
        if(opDevice.isPresent()) {
            Device device = opDevice.get();
            this.deviceRepository.delete(device);
        } else {
            throw new DeviceNotFoundException("Device not found with deviceId#" + deviceId);
        }

    }

    @Override
    public Device updateDeviceConfigurationStatus(String deviceConfigStatus, String deviceId) {
        Optional<Device> opDevice = this.deviceRepository.findById(deviceId);
        if(opDevice.isPresent()) {
            Device device = opDevice.get();
            device.setLastModifiedDate(new Date());
            device.setStatus(deviceConfigStatus);
            return this.deviceRepository.save(device);
        } else {
            throw new DeviceNotFoundException("Device not found with deviceId#" + deviceId);
        }
    }

    public void saveBulkDevice(Set<Device> devices) {
        this.deviceRepository.saveAll(devices);
    }
}
