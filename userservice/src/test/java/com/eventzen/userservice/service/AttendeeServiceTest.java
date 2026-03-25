package com.eventzen.userservice.service;

import com.eventzen.userservice.model.Attendee;
import com.eventzen.userservice.repository.AttendeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AttendeeServiceTest {

    @Mock
    private AttendeeRepository attendeeRepository;

    @InjectMocks
    private AttendeeService attendeeService;

    private Attendee attendee;

    @BeforeEach
    public void setup() {
        attendee = new Attendee();
        attendee.setId(1L);
        attendee.setUserId(100L);
        attendee.setEventId(200L);
        attendee.setStatus("PENDING");
    }

    @Test
    public void testCreateAttendee() {
        when(attendeeRepository.save(attendee)).thenReturn(attendee);

        Attendee saved = attendeeService.createAttendee(attendee);

        assertThat(saved).isNotNull();
        assertThat(saved.getStatus()).isEqualTo("PENDING");
        assertThat(saved.getUserId()).isEqualTo(100L);
    }

    @Test
    public void testGetAttendeeById() {
        when(attendeeRepository.findById(1L)).thenReturn(Optional.of(attendee));

        Attendee found = attendeeService.getAttendee(1L);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getUserId()).isEqualTo(100L);
    }

    @Test
    public void testGetAllAttendees() {
        when(attendeeRepository.findAll()).thenReturn(Arrays.asList(attendee));

        List<Attendee> attendees = attendeeService.getAllAttendees();

        assertThat(attendees).hasSize(1);
        assertThat(attendees.get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void testDeleteAttendee() {
        Attendee attendee = new Attendee();
        attendee.setId(1L);
        attendee.setUserId(1L);
        attendee.setEventId(2L);
        attendee.setStatus("PENDING");

        when(attendeeRepository.findById(1L)).thenReturn(Optional.of(attendee));
        attendeeService.deleteAttendee(1L);
        Mockito.verify(attendeeRepository).delete(attendee);
    }
}