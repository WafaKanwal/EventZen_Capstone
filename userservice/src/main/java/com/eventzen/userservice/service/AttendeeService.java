package com.eventzen.userservice.service;

import com.eventzen.userservice.exception.ResourceNotFoundException;
import com.eventzen.userservice.model.Attendee;
import com.eventzen.userservice.repository.AttendeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    public AttendeeService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public Attendee createAttendee(Attendee attendee) {
        return attendeeRepository.save(attendee);
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Attendee getAttendee(Long id) {
        return attendeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendee not found"));
    }

    public void deleteAttendee(Long id) {
        Attendee attendeeRepository = getAttendee(id);
        this.attendeeRepository.delete(attendeeRepository);
    }
}