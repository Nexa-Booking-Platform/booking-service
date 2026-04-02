package com.bookingsystem.booking_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.booking.topics.booking-created:booking-created-topic}")
    private String bookingCreatedTopic;

    @Bean
    public NewTopic bookingCreatedTopic() {
        return TopicBuilder.name(bookingCreatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}