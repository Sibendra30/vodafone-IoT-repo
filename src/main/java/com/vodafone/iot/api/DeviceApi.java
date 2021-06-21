package com.vodafone.iot.api;

import com.vodafone.iot.model.Device;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("/device")
public interface DeviceApi {

    @GetMapping
    ResponseEntity<Set<Device>> getDevice(@RequestParam("simStatus") String simStatus);

    @PatchMapping("/{deviceId}")
    ResponseEntity<Device> updateDeviceStatus(@PathVariable("deviceId") String deviceId, @RequestBody Device device);

    @DeleteMapping("/deviceId")
    void deleteDevice(@PathVariable("deviceId") String deviceId);
}
