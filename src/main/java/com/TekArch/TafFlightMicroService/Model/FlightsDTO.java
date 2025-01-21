package com.TekArch.TafFlightMicroService.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightsDTO {

    private Long id;
    private String flightNumber;
    private String departure; // City/Airport
    private String arrival; // City/Airport
    private LocalDateTime departureTime; // Departure Time
    private LocalDateTime arrivalTime; // Arrival Time
    private Double price; // Price
    private Integer availableSeats; // Available Seats
    private LocalDateTime createdAt; // Record creation timestamp
    private LocalDateTime updatedAt;
}
