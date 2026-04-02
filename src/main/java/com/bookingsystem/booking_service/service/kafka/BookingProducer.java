package com.bookingsystem.booking_service.service.kafka;

import com.bookingsystem.booking_service.dto.BookingCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class BookingProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.booking.topics.booking-created:booking-created-topic}")
    private String topic;

    public BookingProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingCreatedEvent(BookingCreatedEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
