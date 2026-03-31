package com.bookingsystem.booking_service.dto;

import lombok.Data;

@Data
public class BookingRequest {

    private String propertyId;
    private String checkIn;
    private String checkOut;
}
