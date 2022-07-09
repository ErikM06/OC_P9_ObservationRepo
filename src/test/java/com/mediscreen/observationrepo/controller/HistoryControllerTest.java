package com.mediscreen.observationrepo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.observationrepo.ObservationRepoApplication;
import com.mediscreen.observationrepo.model.PatientNote;
import com.mediscreen.observationrepo.services.NoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HistoryController.class)
@ContextConfiguration(classes ={ObservationRepoApplication.class})
public class HistoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NoteService noteService;

    @Test
    void addPatientTest_shouldReturn_201() throws Exception {
        long patIdTest = 1;
        PatientNote patientNote = new PatientNote("idTest",patIdTest, "contentTest", "familyTest");

        given(noteService.addPatient(any(PatientNote.class))).willReturn(patientNote);

        mvc.perform(post("/pat-history/add")
                        .content(asJsonString(patientNote))
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
        Optional<PatientNote> patientTest = Optional.of(new PatientNote(idTest, patIdTest, "contentTest", "familyTest"));

        given(noteService.getPatientHistoryById(idTest)).willReturn(patientTest);
        mvc.perform(get("/pat-history/get-by-id")
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
        PatientNote patientNoteTest1 = new PatientNote(idTest, patIdTest, "contentTest", "familyTest");
        PatientNote patientNoteTest2 = new PatientNote(idTest2, patIdTest, "contentTest2", "familyTest2");
        List<PatientNote> patientNoteListTest = new ArrayList<>(Arrays.asList(patientNoteTest1, patientNoteTest2));

        given(noteService.getPatientHistoryByPatId(patIdTest)).willReturn(patientNoteListTest);
        mvc.perform(get("/pat-history/get-by-pat-id")
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

        PatientNote patientNoteTest1 = new PatientNote(idTest, 1L, "contentTest", "familyTest");
        PatientNote patientNoteTest2 = new PatientNote(idTest2, 2L, "contentTest2", "familyTest2");
        List<PatientNote> patientNoteListTest = new ArrayList<>(Arrays.asList(patientNoteTest1, patientNoteTest2));

        given(noteService.getAllPatientHistory()).willReturn(patientNoteListTest);
        mvc.perform(get("/pat-history/get-all-patient-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    void updatePatientHistory_shouldReturn_202() throws Exception {

        String idTest = "idTest";
        long patIdTest = 1;
        PatientNote patientNoteTestChanged = new PatientNote(idTest, patIdTest, "contentTestHaveChanged", "familyTest");

        given(noteService.updatePatientHistory(any(PatientNote.class))).willReturn(patientNoteTestChanged);

        mvc.perform(post("/pat-history/update-patient-history")
                        .content(asJsonString(patientNoteTestChanged))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.recommendations", Matchers.containsString("contentTestHaveChanged")));
    }

    @Test
    void deletePatientHistory_shouldReturn_202() throws Exception {
        String idTest = "idTest";

        mvc.perform(get("/pat-history/delete-by-id")
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
