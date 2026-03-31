package com.bookingsystem.booking_service.kafka;

import lombok.Getter;

@Getter
public class BookingCreatedEvent {

    private String bookingId;
    private String userId;
    private double amount;

    public BookingCreatedEvent(){}
    public BookingCreatedEvent(String bookingId, String userId, double amount){
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
    }
}
