package com.mediscreen.observationrepo.repository;

import com.mediscreen.observationrepo.model.PatientNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HistRepository extends MongoRepository <PatientNote, String> {


    boolean existsById(String id);

    @Query("{'patId' : ?0 }")
    List<PatientNote> findByPatId(Long id);

}
