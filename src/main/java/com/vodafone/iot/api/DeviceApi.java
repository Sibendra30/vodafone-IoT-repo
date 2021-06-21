package com.vodafone.iot.api;

import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/device")
public interface DeviceApi {

    @GetMapping
    ResponseEntity<PaginatedResponse<Device>> getDevice(@RequestParam(name = "simStatus") String simStatus,
                                                        @RequestParam(name = "pageSize", required = false) int pageSize,
                                                        @RequestParam(name = "pageNumber", required = false) int pageNumber);

    @PatchMapping("/{deviceId}")
    ResponseEntity<Device> updateDeviceStatus(@PathVariable("deviceId") String deviceId, @RequestBody Device device);

    @DeleteMapping("/{deviceId}")
    ResponseEntity deleteDevice(@PathVariable("deviceId") String deviceId);
}
