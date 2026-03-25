package com.eventzen.eventbookingservice.controller;

import com.eventzen.eventbookingservice.model.Booking;
import com.eventzen.eventbookingservice.model.BookingRequest;
import com.eventzen.eventbookingservice.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody @Valid BookingRequest requestBody,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!role.equals("CUSTOMER") && !role.equals("ATTENDEE")) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(bookingService.createBooking(requestBody));
    }

    @GetMapping
    public List<Booking> getBookings(@RequestParam(required = false) Long customerId) {
        if (customerId != null) {
            return bookingService.getBookingsByCustomerId(customerId);
        }
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!role.equals("CUSTOMER") && !role.equals("ATTENDEE")) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable Long id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(bookingService.approveBooking(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable Long id,
            HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(bookingService.rejectBooking(id));
    }
}