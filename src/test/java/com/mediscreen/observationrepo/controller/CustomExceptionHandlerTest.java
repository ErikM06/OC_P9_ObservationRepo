package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.ObservationRepoApplication;
import com.mediscreen.observationrepo.customExceptions.PatHistIdNotFoundException;
import com.mediscreen.observationrepo.services.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HistoryController.class)
@ContextConfiguration(classes ={ObservationRepoApplication.class})
public class CustomExceptionHandlerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NoteService noteService;

    @Test
    public void handleNotFoundException () throws Exception {

        long id = 1L;
        given(noteService.getPatientHistoryByPatId(any(Long.class))).willThrow(PatHistIdNotFoundException.class);

        mvc.perform(get("/pat-history/get-by-pat-id")
                        .param("id",Long.toString(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
