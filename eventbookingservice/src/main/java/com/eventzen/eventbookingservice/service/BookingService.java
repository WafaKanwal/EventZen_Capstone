package com.eventzen.eventbookingservice.service;

import com.eventzen.eventbookingservice.exception.AlreadyPresentException;
import com.eventzen.eventbookingservice.exception.ResourceNotFoundException;
import com.eventzen.eventbookingservice.model.Booking;
import com.eventzen.eventbookingservice.model.BookingRequest;
import com.eventzen.eventbookingservice.model.Event;
import com.eventzen.eventbookingservice.repository.BookingRepository;
import com.eventzen.eventbookingservice.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;

    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
    }

    public Booking createBooking(BookingRequest request) {
        if (request == null || request.getEventId() == null || request.getCustomerId() == null) {
            throw new IllegalArgumentException("Both eventId and customerId must be provided");
        }

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + request.getEventId()));

        boolean exists = bookingRepository.existsByEvent_IdAndCustomerId(
                request.getEventId(),
                request.getCustomerId());
        if (exists) {
            throw new AlreadyPresentException("Booking already present for this customer and event");
        }

        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setCustomerId(request.getCustomerId());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        booking.setStatus("CANCELED");
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookingsAdmin() {
        return bookingRepository.findAll();
    }

    public Booking approveBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        booking.setStatus("APPROVED");
        return bookingRepository.save(booking);
    }

    public Booking rejectBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        booking.setStatus("REJECTED");
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerIdWithEvent(customerId);
    }
}
