package com.bookingsystem.booking_service.consumer;

import com.bookingsystem.booking_service.entity.Booking;
import com.bookingsystem.booking_service.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentSuccessConsumer {

    private final BookingRepository bookingRepository;

    public PaymentSuccessConsumer(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @KafkaListener(topics = "${kafka.payment.topics.payment-success:payment-success-topic}", groupId = "booking-group")
    public void consume(Object bookingId) {

        String id = bookingId.toString();
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("CONFIRMED");

        bookingRepository.save(booking);

        log.info("Booking confirmed: {}", bookingId);
    }
}
