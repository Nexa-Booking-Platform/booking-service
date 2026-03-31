package com.bookingsystem.booking_service.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;
    private String propertyId;

    private String checkIn;
    private String checkOut;

    private String status; // pending, confirmed, cancelled

    private double totalAmount;

}
