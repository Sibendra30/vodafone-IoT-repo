package com.vodafone.iot.repository;

import com.vodafone.iot.model.Sim;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SimRepository extends CrudRepository<Sim, String> {
    Set<Sim> findByStatus(String status);
}
