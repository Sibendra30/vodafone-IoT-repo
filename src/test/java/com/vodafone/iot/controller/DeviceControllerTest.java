package com.vodafone.iot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.controller.DeviceController;
import com.vodafone.iot.error.DeviceNotFoundException;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.PaginatedResponse;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.service.DeviceService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    void getDevicesByStatus() throws Exception {
        Device expectedDevice = new Device("device1", "Device-1", "Device 1", "READY",
                new Sim("sim1", "OP1", "GB", "Active", new Date(), null),
                new Date(), null);
        Page<Device> serviceResponse = new PageImpl<>(Collections.singletonList(expectedDevice), PageRequest.of(1, 10), 1);
        when(deviceService.getDeviceBySimStatus("Active", 10, 1)).thenReturn(serviceResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/device")
                .param("simStatus", "Active")
                .param("pageNumber", "1")
                .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metadata", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].id", Matchers.is("device1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].status", Matchers.is("READY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].sim", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].sim.status", Matchers.is("Active")))
                .andReturn();
    }

    @Test
    void getDevicesByStatus_failure() throws Exception {
        when(deviceService.getDeviceBySimStatus("Active", 10, 1))
                .thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(MockMvcRequestBuilders.get("/device")
                .param("simStatus", "Active")
                .param("pageNumber", "1")
                .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("internalServerError")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Something went wrong. Please contact system admin")))
                .andReturn();
    }

    @Test
    void updateDeviceConfigStatus() throws Exception {
        Device expectedDevice = new Device("device1", "Device-1", "Device 1", "READY",
                new Sim("sim1","OP1", "GB", "Active", new Date(), null),
                new Date(), null);
        when(deviceService.updateDeviceConfigurationStatus("READY", "device1")).thenReturn(expectedDevice);
        mockMvc.perform(MockMvcRequestBuilders.patch("/device/device1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new HashMap() {{put("status", "READY");}})))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("device1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("READY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sim", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sim.status", Matchers.is("Active")))
                .andReturn();
    }

    @Test
    void updateDeviceConfigStatus_invalidDeviceId() throws Exception {
        when(deviceService.updateDeviceConfigurationStatus("READY", "device1"))
                .thenThrow(new DeviceNotFoundException("Device not found with deviceId#device1"));
        mockMvc.perform(MockMvcRequestBuilders.patch("/device/device1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new HashMap() {{put("status", "READY");}})))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("invalidDeviceId")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Device not found with deviceId#device1")))
                .andReturn();
    }

    @Test
    void deleteDevice() throws Exception {
        doNothing().when(deviceService).deleteDevice("device1");
        mockMvc.perform(MockMvcRequestBuilders.delete("/device/device1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
                .andReturn();
    }

    @Test
    void deleteDevice_invalidDeviceId() throws Exception {
        doThrow(new DeviceNotFoundException("Device not found with deviceId#device1"))
                .when(deviceService).deleteDevice("device1");
        mockMvc.perform(MockMvcRequestBuilders.delete("/device/device1"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("invalidDeviceId")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Device not found with deviceId#device1")))
                .andReturn();
    }
}
