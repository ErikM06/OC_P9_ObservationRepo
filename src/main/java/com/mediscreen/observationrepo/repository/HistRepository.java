package com.mediscreen.observationrepo.repository;

import com.mediscreen.observationrepo.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface HistRepository extends MongoRepository <Patient, String> {

    @Query("{ 'id' : ?0 }")
    boolean existsById(String id);
}
