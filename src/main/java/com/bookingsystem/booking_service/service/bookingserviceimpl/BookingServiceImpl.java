package com.bookingsystem.booking_service.service.bookingserviceimpl;

import com.bookingsystem.booking_service.entity.Availability;
import com.bookingsystem.booking_service.entity.Booking;
import com.bookingsystem.booking_service.dto.BookingRequest;
import com.bookingsystem.booking_service.dto.BookingResponse;
import com.bookingsystem.booking_service.dto.BookingCreatedEvent;
import com.bookingsystem.booking_service.repository.AvailabilityRepository;
import com.bookingsystem.booking_service.repository.BookingRepository;
import com.bookingsystem.booking_service.service.BookingService;
import com.bookingsystem.booking_service.service.kafka.BookingProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityRepository availabilityRepository;
    private final BookingProducer bookingProducer;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              AvailabilityRepository availabilityRepository,
                              BookingProducer bookingProducer) {
        this.bookingRepository = bookingRepository;
        this.availabilityRepository = availabilityRepository;
        this.bookingProducer = bookingProducer;
    }


    @Override
    @Transactional
    public BookingResponse createBooking(String userId, BookingRequest request) {

        Availability availability = availabilityRepository
                .findByPropertyIdAndDate(request.getPropertyId(), request.getCheckIn())
                .orElseThrow(() -> new RuntimeException("No availability for the requested date"));

        if (availability.getSlotsAvailable() <= 0) {
            throw new RuntimeException("Fully booked for the requested date");
        }

        // reduce availability
        availability.setSlotsAvailable(availability.getSlotsAvailable() - 1);
        availabilityRepository.save(availability);

        Booking booking = Booking.builder()
                .userId(userId)
                .propertyId(request.getPropertyId())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .status("PENDING")
                .totalAmount(100.0) // demo logic
                .build();

        booking = bookingRepository.save(booking);

        // send Kafka event
        BookingCreatedEvent event = BookingCreatedEvent.builder()
                .bookingId(booking.getId())
                .userId(userId)
                .amount(booking.getTotalAmount())
                .build();

        bookingProducer.sendBookingCreatedEvent(event);

        return mapToResponse(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .propertyId(booking.getPropertyId())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .status(booking.getStatus())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}