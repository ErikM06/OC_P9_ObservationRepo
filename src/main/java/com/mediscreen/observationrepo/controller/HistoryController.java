package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.customExceptions.PatHistIdNotFoundException;
import com.mediscreen.observationrepo.model.Patient;
import com.mediscreen.observationrepo.services.HistoryService;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @PostMapping ("/add")
    public Patient addPatientHistory (@RequestBody Patient patient){
        return historyService.addPatient(patient);
    }

    @GetMapping ("/getById")
    public Patient getPatientHistById (@RequestParam String id) throws PatHistIdNotFoundException {
        Optional<Patient> patientHist;
        patientHist = historyService.getPatientHistoryById(id);
        if (patientHist.isEmpty()){
            throw  new NotFoundException("Patient with id: "+id+ " not found !");
        }
        return patientHist.get() ;
    }

    @GetMapping ("/getByPatId")
    public List<Patient> getPatientHistByPatId (@RequestParam Long id) throws PatHistIdNotFoundException {
        List<Patient> patientHistLs;
        patientHistLs = historyService.getPatientHistoryByPatId(id);
        if (patientHistLs.isEmpty()){
            throw new PatHistIdNotFoundException("Patient hist with id: "+id+ " not found !");
        }
        return patientHistLs ;
    }

    @GetMapping ("/getAllPatientHistory")
    public List<Patient> getAllPatientsHist(){
        List<Patient> allPatientHistoryLs = historyService.getAllPatientHistory();
        return allPatientHistoryLs;
    }

    @PostMapping ("/updatePatientHistory")
    public Patient updatePatientHistory(@RequestParam Patient patient) {
        Patient patientToUpdate = null;
        try {
            patientToUpdate = historyService.updatePatientHistory(patient);
        }
        catch (PatHistIdNotFoundException e){
            e.getMessage();
        }
        return patientToUpdate;
    }

    @GetMapping ("/deleteById")
    public void deletePatHistoryById (@RequestParam String id){
        try {
            historyService.deletePatientHistoryById(id);
        } catch (PatHistIdNotFoundException e){
            e.getMessage();
        }

    }



}
