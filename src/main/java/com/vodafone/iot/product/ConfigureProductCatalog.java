package com.vodafone.iot.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.model.Device;
import com.vodafone.iot.model.Sim;
import com.vodafone.iot.service.DeviceServiceImpl;
import com.vodafone.iot.service.SimServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Component
@Slf4j
public class ConfigureProductCatalog {

    private final String deviceCatalogFilename;
    private final String simCatalogFilename;
    private final DeviceServiceImpl deviceService;
    private final SimServiceImpl simService;
    private final ObjectMapper objectMapper;

    public ConfigureProductCatalog(@Value("${device.catalog.file}") String deviceCatalogFilename,
                                   @Value("${sim.catalog.file}") String simCatalogFilename,
                                   DeviceServiceImpl deviceService,
                                   SimServiceImpl simService, ObjectMapper objectMapper) throws IOException, URISyntaxException {
        this.deviceCatalogFilename = deviceCatalogFilename;
        this.simCatalogFilename = simCatalogFilename;
        this.deviceService = deviceService;
        this.simService = simService;
        this.objectMapper = objectMapper;
        this.insertCatalogProduct();
    }

    public void insertCatalogProduct() throws URISyntaxException, IOException {
        byte [] simCatalogFileContent = Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource(simCatalogFilename)
                        .toURI()));

        Set<Sim> sims = this.objectMapper.readValue(simCatalogFileContent, new TypeReference<Set<Sim>>(){});
        this.simService.saveBulkSim(sims);

        byte [] deviceCatalogFileContent = Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource(deviceCatalogFilename)
                        .toURI()));
        Set<Device> devices = this.objectMapper.readValue(deviceCatalogFileContent, new TypeReference<Set<Device>>(){});
        this.deviceService.saveBulkDevice(devices);
    }
}
