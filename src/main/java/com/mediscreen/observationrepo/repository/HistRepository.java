package com.mediscreen.observationrepo.repository;

import com.mediscreen.observationrepo.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HistRepository extends MongoRepository <Patient, String> {


    boolean existsById(String id);

    @Query("{'patId' : ?0 }")
    List<Patient> findByPatId(Long id);
}
