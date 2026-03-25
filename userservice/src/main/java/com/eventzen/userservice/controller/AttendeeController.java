package com.eventzen.userservice.controller;

import com.eventzen.userservice.model.Attendee;
import com.eventzen.userservice.service.AttendeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public Attendee createAttendee(@RequestBody Attendee attendee) {
        return attendeeService.createAttendee(attendee);
    }

    @GetMapping
    public List<Attendee> getAllAttendees() {
        return attendeeService.getAllAttendees();
    }

    @GetMapping("/{id}")
    public Attendee getAttendee(@PathVariable Long id) {
        return attendeeService.getAttendee(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
    }
}