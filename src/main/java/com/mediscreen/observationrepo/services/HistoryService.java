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

    public Optional<Patient> getPatientHistoryById (String id) throws PatHistIdNotFoundException {
        Optional<Patient> patientHist;
        patientHist = histRepository.findById(id);
        if (patientHist.isEmpty()){
            throw new PatHistIdNotFoundException("For Patient history id: "+id+" no history found!");
        }
        return patientHist;
    }

    @Transactional
    public Patient updatePatientHistory( Patient patient) throws PatHistIdNotFoundException {
        Optional<Patient> patientOpt = histRepository.findById(patient.getId());
        if (patientOpt.isEmpty()){
            throw  new PatHistIdNotFoundException("For Patient history id: "+patient.getId()+" no history found!");
        }
        Patient patientToChange = patientOpt.get();
        patientToChange.setContent(patient.getContent());

       return histRepository.save(patientToChange);
    }
    @Transactional
    public void deletePatientHistoryById(String id) throws PatHistIdNotFoundException {
        if (!histRepository.existsById(id)){
            throw  new PatHistIdNotFoundException("For Patient history id: "+id+" no history found!");
        }
        histRepository.deleteById(id);
    }

    public List<Patient>getPatientHistoryByPatId(Long id) throws PatHistIdNotFoundException {
        List<Patient> patientHistLs;
        patientHistLs = histRepository.findByPatId(id);
        if (patientHistLs.isEmpty()){
            throw new PatHistIdNotFoundException("For Patient id: "+id+" no history found!");
        }
        return patientHistLs;

    }
}
