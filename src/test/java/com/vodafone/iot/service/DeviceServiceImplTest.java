package com.vodafone.iot.service;

import com.vodafone.iot.error.DeviceNotFoundException;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(DeviceServiceImpl.class)
public class DeviceServiceImplTest {

    private DeviceService deviceService;

    @MockBean
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setup() {
        deviceService = new DeviceServiceImpl(deviceRepository);
    }

    @Test
    void getDeviceBySimStatus() {
        Device expectedDevice = new Device("device1", "Device-1", "Device 1", "READY",
                new Sim("sim1", "OP1", "GB", "Active", new Date(), null),
                new Date(), null);
        Page<Device> expectedResponse = new PageImpl<>(Collections.singletonList(expectedDevice), PageRequest.of(1, 10), 1);
        when(deviceRepository
                .findDeviceByStatusAndSim_StatusOrderByCreatedDateDesc("READY", "Active", PageRequest.of(0, 10)))
        .thenReturn(expectedResponse);
        Page<Device> actualResponse =deviceService.getDeviceBySimStatus("Active", 10, 1);
        assertEquals(expectedResponse, actualResponse);
        verify(deviceRepository)
                .findDeviceByStatusAndSim_StatusOrderByCreatedDateDesc("READY", "Active", PageRequest.of(0, 10));
    }

    @Test
    void updateDeviceConfigStatus() {
        Device expectedDevice = new Device("device1", "Device-1", "Device 1", "READY",
                new Sim("sim1", "OP1", "GB", "Active", new Date(), null),
                new Date(), null);
        when(deviceRepository.findById("device1")).thenReturn(Optional.of(expectedDevice));
        when(deviceRepository.save(expectedDevice)).thenReturn(expectedDevice);
        Device actualResponse = deviceService.updateDeviceConfigurationStatus("READY", "device1");
        assertEquals(expectedDevice, actualResponse);
        verify(deviceRepository).findById("device1");
        verify(deviceRepository).save(expectedDevice);
    }

    @Test
    void updateDeviceConfigStatus_invalidDeviceId() {
        when(deviceRepository.findById("device1")).thenReturn(Optional.empty());
        assertThrows(DeviceNotFoundException.class,
                () -> deviceService.updateDeviceConfigurationStatus("READY", "device1"));
    }

    @Test
    void deleteDevice() {
        Device expectedDevice = new Device("device1", "Device-1", "Device 1", "READY",
                new Sim("sim1", "OP1", "GB", "Active", new Date(), null),
                new Date(), null);
        when(deviceRepository.findById("device1")).thenReturn(Optional.of(expectedDevice));
        deviceService.deleteDevice("device1");
        verify(deviceRepository).delete(expectedDevice);
    }

    @Test
    void deleteDevice_InvalidDeviceId() {
        when(deviceRepository.findById("device1")).thenReturn(Optional.empty());
        assertThrows(DeviceNotFoundException.class,
                () -> deviceService.deleteDevice("device1"));
    }
}
