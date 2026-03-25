package com.eventzen.eventbookingservice.controller;

import com.eventzen.eventbookingservice.model.Booking;
import com.eventzen.eventbookingservice.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/bookings")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings(HttpServletRequest request) {

        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        return ResponseEntity.ok(bookingService.getAllBookingsAdmin());
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