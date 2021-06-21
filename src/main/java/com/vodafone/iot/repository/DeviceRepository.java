package com.vodafone.iot.repository;

import com.vodafone.iot.model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DeviceRepository extends CrudRepository<Device, String> {

    Device[] findByName(String name);

    Iterable<Device> findBySimIdIn(List<String> simIds);
}