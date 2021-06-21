package com.vodafone.iot.repository;

import com.vodafone.iot.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface DeviceRepository extends PagingAndSortingRepository<Device, String> {
    Page<Device> findDeviceByStatusAndSim_StatusOrderByCreatedDateDesc(String deviceStatus, String status, Pageable pageInfo);
}