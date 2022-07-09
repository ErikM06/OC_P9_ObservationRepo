package com.mediscreen.observationrepo.services;

import com.mediscreen.observationrepo.customExceptions.PatHistIdNotFoundException;
import com.mediscreen.observationrepo.model.PatientNote;
import com.mediscreen.observationrepo.repository.HistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class NoteService {

    @Autowired
    HistRepository histRepository;

    public PatientNote addPatient (PatientNote patientNote){
    return histRepository.save(patientNote);
    }

    public List<PatientNote> getAllPatientHistory (){
        return histRepository.findAll();
    }

    public Optional<PatientNote> getPatientHistoryById (String id) throws PatHistIdNotFoundException {
        Optional<PatientNote> patientHist;
        patientHist = histRepository.findById(id);
        if (patientHist.isEmpty()){
            throw new PatHistIdNotFoundException("For Patient history id: "+id+" no history found!");
        }
        return patientHist;
    }

    @Transactional
    public PatientNote updatePatientHistory(PatientNote patientNote) throws PatHistIdNotFoundException {
        Optional<PatientNote> patientOpt = histRepository.findById(patientNote.getId());
        if (patientOpt.isEmpty()){
            throw  new PatHistIdNotFoundException("For Patient history id: "+ patientNote.getId()+" no history found!");
        }
        PatientNote patientNoteToChange = patientOpt.get();
        patientNoteToChange.setContent(patientNote.getContent());

       return histRepository.save(patientNoteToChange);
    }
    @Transactional
    public void deletePatientHistoryById(String id) throws PatHistIdNotFoundException {
        if (!histRepository.existsById(id)){
            throw  new PatHistIdNotFoundException("For Patient history id: "+id+" no history found!");
        }
        histRepository.deleteById(id);
    }

    public List<PatientNote>getPatientHistoryByPatId(Long id) throws PatHistIdNotFoundException {
        List<PatientNote> patientNoteHistLS;
        patientNoteHistLS = histRepository.findByPatId(id);
        if (patientNoteHistLS.isEmpty()){
            throw new PatHistIdNotFoundException("For Patient id: "+id+" no history found!");
        }
        return patientNoteHistLS;

    }
}
