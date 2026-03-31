package com.bookingsystem.booking_service.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String propertyId;
    private String date;
    private int slotsAvailable;
}
