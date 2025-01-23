package com.TekArch.TafFlightMicroService.Controller;

import com.TekArch.TafFlightMicroService.Model.FlightsDTO;
import com.TekArch.TafFlightMicroService.Services.FlightServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
@Data
public class FlightController {

    @Autowired
    private final FlightServiceImpl flightServiceImpl;

    @GetMapping
    public ResponseEntity<List<FlightsDTO>> getAllFlights() {
        try {
            return ResponseEntity.ok(flightServiceImpl.getAllFlights());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{flightid}")
    public ResponseEntity<Optional<FlightsDTO>> getFlightById(@PathVariable Long flightid) {
        Optional<FlightsDTO> flight = flightServiceImpl.getFlightById(flightid);
        if (flight == null) {
            // Return 404 if user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Return 200 OK if user is found
        return ResponseEntity.ok(flight);
    }

    @PostMapping("/addFlight")
    public ResponseEntity<FlightsDTO> addFlight(@RequestBody FlightsDTO flightobj) {
        try {
            return ResponseEntity.ok(flightServiceImpl.addFlight(flightobj));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<FlightsDTO> updateFlight(@PathVariable Long flightId, @RequestBody FlightsDTO flightDetails)
    {
        try { FlightsDTO updatedFlight = flightServiceImpl.updateFlight(flightId, flightDetails);
            return ResponseEntity.ok(updatedFlight); }
        catch (RuntimeException e)
        {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{flightid}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long flightid) {
        try {
            flightServiceImpl.deleteFlight(flightid);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
