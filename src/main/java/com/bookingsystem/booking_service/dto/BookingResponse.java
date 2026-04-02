package com.bookingsystem.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private String id;
    private String userId;
    private String propertyId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}