package com.eventzen.userservice.controller;

import com.eventzen.userservice.model.Attendee;
import com.eventzen.userservice.service.AttendeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AttendeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AttendeeService attendeeService;

    @InjectMocks
    private AttendeeController attendeeController;

    private Attendee attendee;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        attendee = new Attendee();
        attendee.setId(1L);
        attendee.setUserId(100L);
        attendee.setEventId(200L);
        attendee.setStatus("PENDING");

        mockMvc = MockMvcBuilders.standaloneSetup(attendeeController).build();
    }

    @Test
    public void testCreateAttendee() throws Exception {
        Mockito.when(attendeeService.createAttendee(Mockito.any()))
                .thenReturn(attendee);

        String json = objectMapper.writeValueAsString(attendee);

        mockMvc.perform(post("/attendees")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(100))
                .andExpect(jsonPath("$.eventId").value(200))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    public void testGetAllAttendees() throws Exception {
        Mockito.when(attendeeService.getAllAttendees())
                .thenReturn(Arrays.asList(attendee));

        mockMvc.perform(get("/attendees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(100))
                .andExpect(jsonPath("$[0].eventId").value(200));
    }

    @Test
    public void testGetAttendeeById() throws Exception {
        Mockito.when(attendeeService.getAttendee(1L))
                .thenReturn(attendee);

        mockMvc.perform(get("/attendees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(100))
                .andExpect(jsonPath("$.eventId").value(200));
    }

    @Test
    public void testDeleteAttendee() throws Exception {
        Mockito.doNothing().when(attendeeService).deleteAttendee(1L);

        mockMvc.perform(delete("/attendees/1"))
                .andExpect(status().isOk());
    }
}