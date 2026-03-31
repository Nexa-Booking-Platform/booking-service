package com.bookingsystem.booking_service.repository;

import com.bookingsystem.booking_service.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, String> {

    Optional<Availability> findByPropertyIdAndDate(String propertyId, String date);
}
