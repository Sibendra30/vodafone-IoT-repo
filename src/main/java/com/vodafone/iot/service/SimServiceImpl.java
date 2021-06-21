package com.vodafone.iot.service;

import com.vodafone.iot.model.Sim;
import com.vodafone.iot.repository.SimRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SimServiceImpl implements SimService {

    private final SimRepository simRepository;

    public SimServiceImpl(SimRepository simRepository) {
        this.simRepository = simRepository;
    }

    public Set<Sim> getSimByStatus(String filter) {
        return this.simRepository.findByStatus(filter);
    }

    public void saveBulkSim(Iterable<Sim> sims) {
        this.simRepository.saveAll(sims);
    }
}
