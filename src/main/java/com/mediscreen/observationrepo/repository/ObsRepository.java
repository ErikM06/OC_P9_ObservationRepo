package com.mediscreen.observationrepo.repository;

import com.mediscreen.observationrepo.model.Observation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ObsRepository extends MongoRepository <Observation, String> {
}
