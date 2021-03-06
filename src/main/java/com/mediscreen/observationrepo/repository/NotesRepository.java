package com.mediscreen.observationrepo.repository;

import com.mediscreen.observationrepo.model.PatientNotes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NotesRepository extends MongoRepository <PatientNotes, String> {


    boolean existsById(String id);

    @Query("{'patId' : ?0 }")
    List<PatientNotes> findByPatId(Long id);

}
