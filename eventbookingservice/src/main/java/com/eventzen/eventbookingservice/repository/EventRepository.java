package com.eventzen.eventbookingservice.repository;

import com.eventzen.eventbookingservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByNameAndVenueId(String name, Long venueId);
}