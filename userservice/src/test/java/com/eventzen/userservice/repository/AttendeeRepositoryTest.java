package com.eventzen.userservice.repository;

import com.eventzen.userservice.model.Attendee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttendeeRepositoryTest {

    @Autowired
    private AttendeeRepository attendeeRepository;

    private Attendee savedAttendee;

    @BeforeEach
    void setup() {
        attendeeRepository.deleteAll();

        Attendee attendee = new Attendee();
        attendee.setUserId(1L);
        attendee.setEventId(2L);
        attendee.setStatus("PENDING");

        savedAttendee = attendeeRepository.save(attendee);
    }

    @AfterEach
    void tearDown() {
        attendeeRepository.deleteAll();
        System.out.println("After each test");
    }

    @Test
    void testSaveAttendee() {
        assertNotNull(savedAttendee);
        assertEquals(1L, savedAttendee.getUserId());
        assertEquals(2L, savedAttendee.getEventId());
        assertEquals("PENDING", savedAttendee.getStatus());
    }

    @Test
    void testFindAllAttendees() {
        Attendee attendee2 = new Attendee();
        attendee2.setUserId(2L);
        attendee2.setEventId(3L);
        attendee2.setStatus("APPROVED");

        attendeeRepository.save(attendee2);

        List<Attendee> attendees = attendeeRepository.findAll();
        assertEquals(2, attendees.size());
    }

    @Test
    void testFindById() {
        Attendee found = attendeeRepository.findById(savedAttendee.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(1L, found.getUserId());
        assertEquals("PENDING", found.getStatus());
    }

    @Test
    void testDeleteAttendee() {
        attendeeRepository.deleteById(savedAttendee.getId());
        assertEquals(0, attendeeRepository.findAll().size());
        assertNull(attendeeRepository.findById(savedAttendee.getId()).orElse(null));
    }

    @Test
    void testUpdateAttendee() {
        savedAttendee.setStatus("APPROVED");
        Attendee updated = attendeeRepository.save(savedAttendee);

        assertEquals("APPROVED", updated.getStatus());
        assertEquals(savedAttendee.getId(), updated.getId());
    }
}