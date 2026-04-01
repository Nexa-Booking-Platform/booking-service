package com.bookingsystem.booking_service.consumer;

import com.bookingsystem.booking_service.entity.Booking;
import com.bookingsystem.booking_service.repository.BookingRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentSuccessConsumer {

    private final BookingRepository bookingRepository;

    public PaymentSuccessConsumer(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @KafkaListener(topics = "payment-success", groupId = "booking-group")
    public void consume(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("CONFIRMED");

        bookingRepository.save(booking);

        System.out.println("Booking confirmed: " + bookingId);
    }
}
