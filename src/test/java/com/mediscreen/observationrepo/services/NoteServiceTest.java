package com.mediscreen.observationrepo.services;

import com.mediscreen.observationrepo.customExceptions.PatHistIdNotFoundException;
import com.mediscreen.observationrepo.model.PatientNote;
import com.mediscreen.observationrepo.repository.HistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertFalse;
import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations= "classpath:application-test.yml")
@Profile("test")
public class NoteServiceTest {

    @Autowired
    HistRepository histRepository;

    @Autowired
    NoteService noteService;

    PatientNote patientNote = new PatientNote("idTest",1L, "contentTest", "lastNameTest");

    @BeforeEach
    void initTest (){
        histRepository.save(this.patientNote);
    }

    @AfterEach
    void cleanTest (){
        histRepository.deleteAll();
    }


    @Test
    public void getAllPatientNoteTest() {
        List<PatientNote> patientNoteListTest = histRepository.findByPatId(this.patientNote.getPatId());
        assertNotNull(patientNoteListTest);
    }


    @Test
    public void addPatientNoteTest(){
        PatientNote patientNoteTest = new PatientNote("id2Test",2L, "contentTest", "lastNameTest");
        noteService.addPatient(patientNoteTest);
        assertTrue(histRepository.existsById(patientNoteTest.getId()));
    }

    @Test
    public void getPatientNoteById() throws PatHistIdNotFoundException, NullPointerException{
       Optional<PatientNote>patientNoteOptional = noteService.getPatientHistoryById(this.patientNote.getId());
        if (patientNoteOptional.isEmpty()){
            throw new NullPointerException();
        }
       assertNotNull(patientNoteOptional.get());
    }

    @Test
    public void updatePatientNote() throws PatHistIdNotFoundException, NullPointerException {
        String newContent = "New content for PatientNote";
        Optional<PatientNote>patientNoteOptional = noteService.getPatientHistoryById(this.patientNote.getId());
        if (patientNoteOptional.isEmpty()){
            throw new NullPointerException();
        }
        patientNoteOptional.get().setContent(newContent);
        noteService.updatePatientHistory(patientNoteOptional.get());
        Optional<PatientNote> updatedPatient = histRepository.findById(patientNoteOptional.get().getId());
        if (updatedPatient.isEmpty()){
            throw new NullPointerException();
        }
        assertEquals(updatedPatient.get().getContent(),newContent);
    }

    @Test
    public void deletePatientNote() throws PatHistIdNotFoundException {
        noteService.deletePatientHistoryById(this.patientNote.getId());
        assertFalse(histRepository.existsById(this.patientNote.getId()));
    }

    @Test
    public void getPatientNoteByPatId() throws PatHistIdNotFoundException {
     List<PatientNote> patientHistoryByPatId = noteService.getPatientHistoryByPatId(this.patientNote.getPatId());
     assertNotNull(patientHistoryByPatId);
    }
}
