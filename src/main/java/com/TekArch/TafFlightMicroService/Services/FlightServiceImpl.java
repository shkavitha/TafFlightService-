package com.TekArch.TafFlightMicroService.Services;

import com.TekArch.TafFlightMicroService.Model.FlightsDTO;
import com.TekArch.TafFlightMicroService.Services.Interface.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${datastoreServiceUrl}")
    private String crudServiceUrl;

    //Get All Flights
    public List<FlightsDTO> getAllFlights() {
        try {
            // Get the response as an array of User objects
            FlightsDTO[] flightsArray = restTemplate.getForObject(crudServiceUrl, FlightsDTO[].class);
            return Arrays.asList(flightsArray);  // Convert the array to a list
        } catch (HttpClientErrorException e) {
            // Handle errors (e.g., 404, 500, etc.)
            System.out.println("Error fetching users: " + e.getMessage());
            return null;
        }
    }

    //Get Flight detail by flight ID
    public Optional<FlightsDTO> getFlightById(Long flightid) {
        String url = crudServiceUrl + "/" + flightid;
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, FlightsDTO.class));
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("User with ID " + flightid + " not found", e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("Failed to fetch flight: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching user with ID " + flightid, e);
        }
    }

    public FlightsDTO addFlight(FlightsDTO flight) {
        String url = crudServiceUrl;
        try {
            return restTemplate.postForObject(url, flight, FlightsDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("Failed to create Flight: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating Flight", e);
        }
    }

    public void updateFLight(Long flightid, FlightsDTO flightdtls) {
        String url = crudServiceUrl + "/" + flightid;
        try {
            restTemplate.put(url, flightdtls);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Flight with ID " + flightid + " not found", e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("Failed to update FLight: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while updating Flight", e);
        }
    }

    public void deleteFlight(Long flightId) {
        String url = crudServiceUrl + "/" + flightId;
        try {
            restTemplate.delete(url);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Flight with ID " + flightId + " not found", e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("Failed to delete Flight: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while deleting flight", e);
        }
    }
}

