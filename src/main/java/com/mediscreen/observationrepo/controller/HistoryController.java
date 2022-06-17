package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.model.Patient;
import com.mediscreen.observationrepo.services.HistoryService;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Patient> getPatientHistById (@RequestParam String id){
        Optional<Patient> patientHist = historyService.getPatientHistoryById(id);
        if (patientHist.isEmpty()){
            throw  new NotFoundException("Patient with id: "+id+ " not found !");
        }
    return new ResponseEntity<>(patientHist.get(),HttpStatus.OK) ;
    }

    @GetMapping ("/getAllPatientHistory")
    public ResponseEntity<List<Patient>> getAllPatientsHist(){

        List<Patient> allPatientHistoryLs = historyService.getAllPatientHistory();
        return new ResponseEntity<>(allPatientHistoryLs, HttpStatus.OK);


    }



}
