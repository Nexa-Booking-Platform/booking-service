package com.bookingsystem.booking_service.service.bookingserviceimpl;

import com.bookingsystem.booking_service.dto.BookingRequest;
import com.bookingsystem.booking_service.entity.Availability;
import com.bookingsystem.booking_service.entity.Booking;
import com.bookingsystem.booking_service.event.BookingCreatedEvent;
import com.bookingsystem.booking_service.repository.AvailabilityRepository;
import com.bookingsystem.booking_service.repository.BookingRepository;
import com.bookingsystem.booking_service.service.BookingService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityRepository availabilityRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              AvailabilityRepository availabilityRepository,
                              KafkaTemplate<String, Object> kafkaTemplate) {
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Booking createBooking(String userId, BookingRequest request) {

        Availability availability = availabilityRepository
                .findByPropertyIdAndDate(request.getPropertyId(), request.getCheckIn())
                .orElseThrow(() -> new RuntimeException("No availability"));

        if (availability.getSlotsAvailable() <= 0) {
            throw new RuntimeException("Fully booked");
        }

        // reduce availability
        availability.setSlotsAvailable(availability.getSlotsAvailable() - 1);
        availabilityRepository.save(availability);

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setPropertyId(request.getPropertyId());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setStatus("PENDING");
        booking.setTotalAmount(100.0); // demo

        bookingRepository.save(booking);

        // send Kafka event
        BookingCreatedEvent event =
                new BookingCreatedEvent(booking.getId(), userId, booking.getTotalAmount());

        kafkaTemplate.send("booking-created", event);

        return booking;
    }
}