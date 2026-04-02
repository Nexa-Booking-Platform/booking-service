package com.bookingsystem.booking_service.service;

import com.bookingsystem.booking_service.service.BookingService;
import com.bookingsystem.booking_service.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.mockito.Mockito.*;

import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BookingServiceIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private com.bookingsystem.booking_service.repository.AvailabilityRepository availabilityRepository;

    @MockBean
    private com.bookingsystem.booking_service.service.kafka.BookingProducer bookingProducer;

    @Test
    void shouldCallRepositoryWhenCreatingBooking() {
        com.bookingsystem.booking_service.entity.Availability availability = new com.bookingsystem.booking_service.entity.Availability();
        availability.setSlotsAvailable(1);
        when(availabilityRepository.findByPropertyIdAndDate(any(), any()))
                .thenReturn(java.util.Optional.of(availability));
        when(bookingRepository.save(any())).thenReturn(new com.bookingsystem.booking_service.entity.Booking());

        bookingService.createBooking("user-123", new com.bookingsystem.booking_service.dto.BookingRequest());

        verify(bookingRepository, times(1)).save(any());
    }
}