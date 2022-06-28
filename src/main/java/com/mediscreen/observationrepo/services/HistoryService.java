package com.mediscreen.observationrepo.services;

import com.mediscreen.observationrepo.customExceptions.PatHistIdNotFoundException;
import com.mediscreen.observationrepo.model.Patient;
import com.mediscreen.observationrepo.repository.HistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Patient updatePatientHistory(Patient patient) throws PatHistIdNotFoundException {

        if (!histRepository.existsById(patient.getId())){
            throw  new PatHistIdNotFoundException("For Patient id: "+patient.getId()+" no history found!");
        }
       return histRepository.save(patient);
    }
    @Transactional
    public void deletePatientHistoryById(String id) throws PatHistIdNotFoundException {
        if (!histRepository.existsById(id)){
            throw  new PatHistIdNotFoundException("For Patient id: "+id+" no history found!");
        }
        histRepository.deleteById(id);
    }
}
