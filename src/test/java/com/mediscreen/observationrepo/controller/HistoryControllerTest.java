package com.mediscreen.observationrepo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.observationrepo.ObservationRepoApplication;
import com.mediscreen.observationrepo.model.Patient;
import com.mediscreen.observationrepo.services.HistoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
    HistoryService historyService;

    @Test
    void addPatientTest_shouldReturn_201() throws Exception {
        long patIdTest = 1;
        Patient patient = new Patient("idTest",patIdTest, "contentTest", "familyTest");

        Mockito.when(historyService.addPatient(any(Patient.class))).thenReturn(patient);

        mvc.perform(post("/pat-history/add")
                        .content(asJsonString(patient))
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
        Optional<Patient> patientTest = Optional.of(new Patient(idTest, patIdTest, "contentTest", "familyTest"));

        Mockito.when(historyService.getPatientHistoryById(idTest)).thenReturn(patientTest);
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
        Patient patientTest1 = new Patient(idTest, patIdTest, "contentTest", "familyTest");
        Patient patientTest2 = new Patient(idTest2, patIdTest, "contentTest2", "familyTest2");
        List<Patient> patientListTest = new ArrayList<>(Arrays.asList(patientTest1,patientTest2));

        Mockito.when(historyService.getPatientHistoryByPatId(patIdTest)).thenReturn(patientListTest);
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

        Patient patientTest1 = new Patient(idTest, 1L, "contentTest", "familyTest");
        Patient patientTest2 = new Patient(idTest2, 2L, "contentTest2", "familyTest2");
        List<Patient> patientListTest = new ArrayList<>(Arrays.asList(patientTest1,patientTest2));

        Mockito.when(historyService.getAllPatientHistory()).thenReturn(patientListTest);
        mvc.perform(get("/pat-history/get-all-patient-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andReturn();
    }

    @Test
    void updatePatientHistory_shouldReturn_202() throws Exception {

        String idTest = "idTest";
        long patIdTest = 1;
        Patient patientTestChanged = new Patient(idTest, patIdTest, "contentTestHaveChanged", "familyTest");

        Mockito.when(historyService.updatePatientHistory(any(Patient.class))).thenReturn(patientTestChanged);

        mvc.perform(post("/pat-history/update-patient-history")
                        .content(asJsonString(patientTestChanged))
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
