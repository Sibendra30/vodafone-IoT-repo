package com.vodafone.iot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.service.DeviceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeviceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getDevicesByStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/device")
                .param("simStatus", "Active")
                .param("pageNumber", "1")
                .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].id", Matchers.is("device1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].status", Matchers.is("READY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].sim.status", Matchers.is("Active")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metadata", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.pageNumber", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.pageSize", Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.totalElements", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.metadata.totalPages", Matchers.is(1)))
                .andReturn();
    }

    @Test
    @Order(2)
    void updateDeviceConfigStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/device/device16")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new HashMap() {{put("status", "READY");}})))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("device16")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("READY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate", Matchers.notNullValue()))
                .andReturn();
    }

    @Test
    @Order(3)
    void deleteDevice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/device/device16"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
                .andReturn();
    }

    @Test
    @Order(4)
    void deleteDevice_invalidDeviceId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/device/device16"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("invalidDeviceId")))
                .andReturn();
    }

    @Test
    @Order(5)
    void updateDeviceConfigStatus_invalidDevice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/device/device162")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new HashMap() {{put("status", "READY");}})))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("invalidDeviceId")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.notNullValue()))
                .andReturn();
    }

}
