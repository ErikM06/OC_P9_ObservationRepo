package com.mediscreen.observationrepo.services;

import com.mediscreen.observationrepo.customExceptions.IdNotFoundException;
import com.mediscreen.observationrepo.customExceptions.PatIdNotFoundException;
import com.mediscreen.observationrepo.model.PatientNotes;
import com.mediscreen.observationrepo.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class NotesService {

    @Autowired
    NotesRepository notesRepository;

    public PatientNotes addPatient (PatientNotes patientNotes){
    return notesRepository.save(patientNotes);
    }

    public List<PatientNotes> getAllPatientNote(){
        return notesRepository.findAll();
    }

    public Optional<PatientNotes> getPatientNoteById(String id) throws IdNotFoundException {
        Optional<PatientNotes> patientHist;
        patientHist = notesRepository.findById(id);
        if (patientHist.isEmpty()){
            throw new IdNotFoundException("in NoteService "+id+" not found!");
        }
        return patientHist;
    }

    @Transactional
    public PatientNotes updatePatientNote(PatientNotes patientNotes) throws PatIdNotFoundException {
        Optional<PatientNotes> patientOpt = notesRepository.findById(patientNotes.getId());
        if (patientOpt.isEmpty()){
            throw  new PatIdNotFoundException("in NoteService patient id: "+ patientNotes.getId()+" not found!");
        }
        PatientNotes patientNotesToChange = patientOpt.get();
        patientNotesToChange.setContent(patientNotes.getContent());

       return notesRepository.save(patientNotesToChange);
    }
    @Transactional
    public void deletePatientNoteById(String id) throws IdNotFoundException {
        if (!notesRepository.existsById(id)){
            throw  new IdNotFoundException("in NoteService "+id+" not found!");
        }
        notesRepository.deleteById(id);
    }

    public List<PatientNotes> getPatientNoteByPatId(Long id) throws PatIdNotFoundException {
        List<PatientNotes> patientNotesHistLS;
        patientNotesHistLS = notesRepository.findByPatId(id);
        if (patientNotesHistLS.isEmpty()){
            throw new PatIdNotFoundException("in NoteService patient id: "+id+ "not found!");
        }
        return patientNotesHistLS;

    }
}
