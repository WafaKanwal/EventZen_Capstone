package com.eventzen.eventbookingservice.service;

import com.eventzen.eventbookingservice.exception.AlreadyPresentException;
import com.eventzen.eventbookingservice.exception.ResourceNotFoundException;
import com.eventzen.eventbookingservice.model.Event;
import com.eventzen.eventbookingservice.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        boolean exists = eventRepository.existsByNameAndVenueId(event.getName(), event.getVenueId());
        if (exists) {
            throw new AlreadyPresentException("Event already present at this venue");
        }

        if (event.getCreatedBy() != null) {
            event.setStatus("PENDING");
        } else {
            event.setStatus("APPROVED");
        }

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDescription(updatedEvent.getDescription());
        existingEvent.setDateTime(updatedEvent.getDateTime());
        existingEvent.setVenueId(updatedEvent.getVenueId());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }
        eventRepository.deleteById(id);
    }

    public Event approveEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        event.setStatus("APPROVED");
        return eventRepository.save(event);
    }

    public Event rejectEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        event.setStatus("REJECTED");
        return eventRepository.save(event);
    }
}