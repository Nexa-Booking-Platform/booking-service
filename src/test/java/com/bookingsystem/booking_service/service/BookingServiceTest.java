package com.bookingsystem.booking_service.service;

import com.bookingsystem.booking_service.dto.BookingCreatedEvent;
import com.bookingsystem.booking_service.dto.BookingRequest;
import com.bookingsystem.booking_service.dto.BookingResponse;
import com.bookingsystem.booking_service.entity.Availability;
import com.bookingsystem.booking_service.entity.Booking;
import com.bookingsystem.booking_service.repository.AvailabilityRepository;
import com.bookingsystem.booking_service.repository.BookingRepository;
import com.bookingsystem.booking_service.service.bookingserviceimpl.BookingServiceImpl;
import com.bookingsystem.booking_service.service.kafka.BookingProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private BookingProducer bookingProducer;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void shouldCreateBookingSuccessfully() {

        // Arrange
        BookingRequest request = new BookingRequest();
        request.setPropertyId("prop-1");
        request.setCheckIn(LocalDate.now());

        String userId = "user-123";

        Availability availability = new Availability();
        availability.setSlotsAvailable(1);
        availability.setDate(request.getCheckIn());

        when(availabilityRepository
                .findByPropertyIdAndDate(any(), any()))
                .thenReturn(Optional.of(availability));

        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(Booking.builder().id("booking-1").build());

        // Act
        BookingResponse result = bookingService.createBooking(userId, request);

        // Assert
        assertNotNull(result);

        verify(bookingRepository).save(any(Booking.class));
        verify(availabilityRepository).save(any(Availability.class));
        verify(bookingProducer)
                .sendBookingCreatedEvent(any(BookingCreatedEvent.class));
    }
}