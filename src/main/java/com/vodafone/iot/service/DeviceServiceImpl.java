package com.vodafone.iot.service;

import com.vodafone.iot.error.DeviceNotFoundException;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Set<Device> getDeviceBySim(Set<String> simIds) {
        Device device = new Device("device1",
                "Device 1",
                "This is device 1",
                "READY",
                new Sim("sim1", "OP1", "GB", "Active", new Date(), null),
                new Date(), null);
        return new HashSet<>((Collection<? extends Device>) this.deviceRepository.findBySimIdIn(new ArrayList<>(simIds)));
    }

    public void deleteDevice(String deviceId) {
        this.deviceRepository.findById(deviceId);
    }

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
