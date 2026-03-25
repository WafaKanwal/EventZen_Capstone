package com.eventzen.eventbookingservice.controller;

import com.eventzen.eventbookingservice.model.Event;
import com.eventzen.eventbookingservice.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role) && !"CUSTOMER".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id,
            @RequestBody Event event,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(eventService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        eventService.deleteEvent(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveEvent(@PathVariable Long id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(eventService.approveEvent(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectEvent(@PathVariable Long id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(eventService.rejectEvent(id));
    }
}