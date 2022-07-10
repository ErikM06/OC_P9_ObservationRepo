package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.customExceptions.IdNotFoundException;
import com.mediscreen.observationrepo.customExceptions.PatIdNotFoundException;
import com.mediscreen.observationrepo.model.PatientNotes;
import com.mediscreen.observationrepo.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pat-notes")
public class NotesController {

    @Autowired
    NotesService notesService;


    @PostMapping ("/add")
    public PatientNotes addPatientNotes(@RequestBody PatientNotes patientNotes, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        return notesService.addPatient(patientNotes);
    }

    @GetMapping ("/get-by-id")
    public PatientNotes getPatientNotesById(@RequestParam String id) throws IdNotFoundException {
        Optional<PatientNotes> patientHist;
        patientHist = notesService.getPatientNoteById(id);
        if (patientHist.isEmpty()){
            throw new IdNotFoundException("Patient notes with id: "+id+ " not found !");
        }
        return patientHist.get() ;
    }

    @GetMapping ("/get-by-pat-id")
    public List<PatientNotes> getPatientNotesByPatId(@RequestParam Long id) throws PatIdNotFoundException {
        List<PatientNotes> patientNotesHistLS;
        try {
            patientNotesHistLS = notesService.getPatientNoteByPatId(id);
        } catch (PatIdNotFoundException e) {
            throw new PatIdNotFoundException(e.getMessage());
        }
        return patientNotesHistLS;
    }

    @GetMapping ("/get-all-patient-notes")
    public List<PatientNotes> getAllPatientsNote(){
        return notesService.getAllPatientNote();
    }

    @PostMapping ("/update-patient-notes")
    public PatientNotes updatePatientNotes(@RequestBody PatientNotes patientNotes, HttpServletResponse httpServletResponse) throws IdNotFoundException {
        PatientNotes patientNotesToUpdate;
        try {
            patientNotesToUpdate = notesService.updatePatientNote(patientNotes);
            httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        catch (IdNotFoundException e){
            throw new IdNotFoundException(e.getMessage());
        }
        return patientNotesToUpdate;
    }

    @GetMapping ("/delete-by-id")
    public void deletePatNotesById(@RequestParam String id, HttpServletResponse httpServletResponse) throws IdNotFoundException {
        try {
            httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            notesService.deletePatientNoteById(id);
        } catch (IdNotFoundException e){
            throw new IdNotFoundException(e.getMessage());
        }


    }



}
