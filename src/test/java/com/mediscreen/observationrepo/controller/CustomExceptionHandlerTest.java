package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.ObservationRepoApplication;
import com.mediscreen.observationrepo.customExceptions.IdNotFoundException;
import com.mediscreen.observationrepo.customExceptions.PatIdNotFoundException;
import com.mediscreen.observationrepo.services.NotesService;
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
@WebMvcTest(NotesController.class)
@ContextConfiguration(classes ={ObservationRepoApplication.class})
public class CustomExceptionHandlerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NotesService notesService;

    @Test
    public void handlePatientIdNotFoundException () throws Exception {

        long id = 1L;

        given(notesService.getPatientNoteByPatId(any(Long.class))).willThrow(PatIdNotFoundException.class);

        mvc.perform(get("/pat-notes/get-by-pat-id")
                        .param("id",Long.toString(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void handleIdNotFoundException() throws Exception {

        long id = 1L;
        given(notesService.getPatientNoteById(any(String.class))).willThrow(IdNotFoundException.class);

        mvc.perform(get("/pat-notes/get-by-id")
                        .param("id",Long.toString(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
