package com.mediscreen.observationrepo.services;

import com.mediscreen.observationrepo.model.Patient;
import com.mediscreen.observationrepo.repository.HistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    HistRepository histRepository;

    public Patient addPatient (Patient patient){
    return histRepository.save(patient);
    }

    public List<Patient> getAllPatientHistory (){
        return histRepository.findAll();
    }

    public Optional<Patient> getPatientHistoryById (String id){
        return histRepository.findById(id);
    }

}
