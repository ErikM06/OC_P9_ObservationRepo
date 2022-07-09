package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.customExceptions.PatHistIdNotFoundException;
import com.mediscreen.observationrepo.model.PatientNote;
import com.mediscreen.observationrepo.services.NoteService;
import com.sun.jersey.api.NotFoundException;
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
@RequestMapping("/pat-history")
public class HistoryController {

    @Autowired
    NoteService noteService;

   /* @PostMapping ("/add")
    public ResponseEntity<Patient> addPatientHistory (@RequestBody Patient patient){
        return new ResponseEntity<>(historyService.addPatient(patient), HttpStatus.CREATED);
    } */

    @PostMapping ("/add")
    public PatientNote addPatientHistory (@RequestBody PatientNote patientNote, HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        return noteService.addPatient(patientNote);
    }

    @GetMapping ("/get-by-id")
    public PatientNote getPatientHistById (@RequestParam String id) throws PatHistIdNotFoundException {
        Optional<PatientNote> patientHist;
        patientHist = noteService.getPatientHistoryById(id);
        if (patientHist.isEmpty()){
            throw  new NotFoundException("Patient with id: "+id+ " not found !");
        }
        return patientHist.get() ;
    }

    @GetMapping ("/get-by-pat-id")
    public List<PatientNote> getPatientHistByPatId (@RequestParam Long id) throws PatHistIdNotFoundException {
        List<PatientNote> patientNoteHistLS;
        patientNoteHistLS = noteService.getPatientHistoryByPatId(id);
        if (patientNoteHistLS.isEmpty()){
            throw new PatHistIdNotFoundException("Patient hist with id: "+id+ " not found !");
        }
        return patientNoteHistLS;
    }

    @GetMapping ("/get-all-patient-history")
    public List<PatientNote> getAllPatientsHist(){
        List<PatientNote> allPatientHistoryLNotes = noteService.getAllPatientHistory();
        return allPatientHistoryLNotes;
    }

    @PostMapping ("/update-patient-history")
    public PatientNote updatePatientHistory(@RequestBody PatientNote patientNote, HttpServletResponse httpServletResponse) {
        PatientNote patientNoteToUpdate = null;
        try {
            patientNoteToUpdate = noteService.updatePatientHistory(patientNote);
            httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        catch (PatHistIdNotFoundException e){
            e.getMessage();
        }
        return patientNoteToUpdate;
    }

    @GetMapping ("/delete-by-id")
    public void deletePatHistoryById (@RequestParam String id, HttpServletResponse httpServletResponse){
        try {
            httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            noteService.deletePatientHistoryById(id);
        } catch (PatHistIdNotFoundException e){
            e.getMessage();
        }

    }



}
