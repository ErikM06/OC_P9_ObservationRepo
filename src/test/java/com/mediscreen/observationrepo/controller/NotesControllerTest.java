package com.mediscreen.observationrepo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.observationrepo.model.PatientNotes;
import com.mediscreen.observationrepo.services.NotesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NotesController.class)
public class NotesControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NotesService notesService;

    @Test
    void addPatientTest_shouldReturn_201() throws Exception {
        long patIdTest = 1;
        PatientNotes patientNotes = new PatientNotes("idTest",patIdTest, "contentTest", "familyTest");

        given(notesService.addPatient(any(PatientNotes.class))).willReturn(patientNotes);

        mvc.perform(post("/pat-notes/add")
                        .content(asJsonString(patientNotes))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    void getPatientHistById_shouldReturn_200() throws Exception {
        String idTest = "idTest";
        long patIdTest = 1;
        Optional<PatientNotes> patientTest = Optional.of(new PatientNotes(idTest, patIdTest, "contentTest", "familyTest"));

        given(notesService.getPatientNoteById(idTest)).willReturn(patientTest);
        mvc.perform(get("/pat-notes/get-by-id")
                        .param("id", idTest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // recommendations is the JsonPath for content field in Patient
                .andExpect(jsonPath("$.recommendations", Matchers.containsString("contentTest")))
                .andReturn();
    }

    @Test
    void getPatientHistByPatId_shouldReturn_200() throws Exception {
        String idTest = "idTest";
        String idTest2 = "idTest2";
        long patIdTest = 1;
        PatientNotes patientNotesTest1 = new PatientNotes(idTest, patIdTest, "contentTest", "familyTest");
        PatientNotes patientNotesTest2 = new PatientNotes(idTest2, patIdTest, "contentTest2", "familyTest2");
        List<PatientNotes> patientNotesListTest = new ArrayList<>(Arrays.asList(patientNotesTest1, patientNotesTest2));

        given(notesService.getPatientNoteByPatId(patIdTest)).willReturn(patientNotesListTest);
        mvc.perform(get("/pat-notes/get-by-pat-id")
                        .param("id", String.valueOf(patIdTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    void getAllPatientHist_shouldReturn_200() throws Exception {

        String idTest = "idTest";
        String idTest2 = "idTest2";

        PatientNotes patientNotesTest1 = new PatientNotes(idTest, 1L, "contentTest", "familyTest");
        PatientNotes patientNotesTest2 = new PatientNotes(idTest2, 2L, "contentTest2", "familyTest2");
        List<PatientNotes> patientNotesListTest = new ArrayList<>(Arrays.asList(patientNotesTest1, patientNotesTest2));

        given(notesService.getAllPatientNote()).willReturn(patientNotesListTest);
        mvc.perform(get("/pat-notes/get-all-patient-notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    void updatePatientHistory_shouldReturn_202() throws Exception {

        String idTest = "idTest";
        long patIdTest = 1;
        PatientNotes patientNotesTestChanged = new PatientNotes(idTest, patIdTest, "contentTestHaveChanged", "familyTest");

        given(notesService.updatePatientNote(any(PatientNotes.class))).willReturn(patientNotesTestChanged);

        mvc.perform(post("/pat-notes/update-patient-notes")
                        .content(asJsonString(patientNotesTestChanged))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.recommendations", Matchers.containsString("contentTestHaveChanged")));
    }

    @Test
    void deletePatientHistory_shouldReturn_202() throws Exception {
        String idTest = "idTest";

        mvc.perform(get("/pat-notes/delete-by-id")
                        .param("id", idTest))
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
