package com.mediscreen.observationrepo.services;

import com.mediscreen.observationrepo.customExceptions.IdNotFoundException;
import com.mediscreen.observationrepo.customExceptions.PatIdNotFoundException;
import com.mediscreen.observationrepo.model.PatientNotes;
import com.mediscreen.observationrepo.repository.NotesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
public class NotesServiceTest {

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    NotesService notesService;

    PatientNotes patientNotes = new PatientNotes("idTest",1L, "contentTest", "lastNameTest");

    @BeforeEach
    void initTest (){
        notesRepository.save(this.patientNotes);
    }

    @AfterEach
    void cleanTest (){
        notesRepository.deleteAll();
    }


    @Test
    public void getAllPatientNoteTest() {
        List<PatientNotes> patientNotesListTest = notesRepository.findByPatId(this.patientNotes.getPatId());
        assertNotNull(patientNotesListTest);
    }


    @Test
    public void addPatientNoteTest(){
        PatientNotes patientNotesTest = new PatientNotes("id2Test",2L, "contentTest", "lastNameTest");
        notesService.addPatient(patientNotesTest);
        assertTrue(notesRepository.existsById(patientNotesTest.getId()));
    }

    @Test
    public void getPatientNoteById() throws IdNotFoundException, NullPointerException{
       Optional<PatientNotes>patientNoteOptional = notesService.getPatientNoteById(this.patientNotes.getId());
        if (patientNoteOptional.isEmpty()){
            throw new NullPointerException();
        }
       assertNotNull(patientNoteOptional.get());
    }

    @Test
    public void updatePatientNote() throws NullPointerException, PatIdNotFoundException, IdNotFoundException {
        String newContent = "New content for PatientNote";
        Optional<PatientNotes>patientNoteOptional = notesService.getPatientNoteById(this.patientNotes.getId());
        if (patientNoteOptional.isEmpty()){
            throw new NullPointerException();
        }
        patientNoteOptional.get().setContent(newContent);
        notesService.updatePatientNote(patientNoteOptional.get());
        Optional<PatientNotes> updatedPatient = notesRepository.findById(patientNoteOptional.get().getId());
        if (updatedPatient.isEmpty()){
            throw new NullPointerException();
        }
        assertEquals(updatedPatient.get().getContent(),newContent);
    }

    @Test
    public void deletePatientNote() throws IdNotFoundException {
        notesService.deletePatientNoteById(this.patientNotes.getId());
        assertFalse(notesRepository.existsById(this.patientNotes.getId()));
    }

    @Test
    public void getPatientNoteByPatId() throws PatIdNotFoundException {
     List<PatientNotes> patientHistoryByPatId = notesService.getPatientNoteByPatId(this.patientNotes.getPatId());
     assertNotNull(patientHistoryByPatId);
    }
}
