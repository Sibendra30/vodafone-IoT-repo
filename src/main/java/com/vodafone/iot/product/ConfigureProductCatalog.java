package com.vodafone.iot.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.service.DeviceServiceImpl;
import com.vodafone.iot.service.SimServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Component
@Slf4j
public class ConfigureProductCatalog {

    private final File deviceCatalogFile;
    private final File simCatalogFile;
    private final DeviceServiceImpl deviceService;
    private final SimServiceImpl simService;
    private final ObjectMapper objectMapper;

    public ConfigureProductCatalog(@Value("${device.catalog.file}") FileSystemResource deviceCatalogFileResource,
                                   @Value("${sim.catalog.file}") FileSystemResource simCatalogFileResource,
                                   DeviceServiceImpl deviceService,
                                   SimServiceImpl simService, ObjectMapper objectMapper) throws IOException, URISyntaxException {
        this.deviceCatalogFile = deviceCatalogFileResource.getFile();
        this.simCatalogFile = simCatalogFileResource.getFile();
        this.deviceService = deviceService;
        this.simService = simService;
        this.objectMapper = objectMapper;
        this.insertCatalogProduct();
    }

    public void insertCatalogProduct() throws IOException {
        Set<Sim> sims = this.objectMapper.readValue(simCatalogFile, new TypeReference<Set<Sim>>(){});
        this.simService.saveBulkSim(sims);
        Set<Device> devices = this.objectMapper.readValue(deviceCatalogFile, new TypeReference<Set<Device>>(){});
        this.deviceService.saveBulkDevice(devices);
    }
}
