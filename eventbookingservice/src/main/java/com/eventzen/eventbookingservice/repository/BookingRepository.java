package com.eventzen.eventbookingservice.repository;

import com.eventzen.eventbookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByEvent_IdAndCustomerId(Long eventId, Long customerId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.event WHERE b.customerId = :customerId")
    List<Booking> findByCustomerIdWithEvent(@Param("customerId") Long customerId);
}