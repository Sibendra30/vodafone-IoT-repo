package com.vodafone.iot.service;

import com.vodafone.iot.model.Sim;

import java.util.Set;

public interface SimService {
    Set<Sim> getSimByStatus(String filter);
}
