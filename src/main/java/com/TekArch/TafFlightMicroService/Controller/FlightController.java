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
    public ResponseEntity<FlightsDTO> getFlightById(@PathVariable Long flightid) {
        Optional<FlightsDTO> flight = flightServiceImpl.getFlightById(flightid);
        return flight.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/addFlight")
    public ResponseEntity<FlightsDTO> addFlight(@RequestBody FlightsDTO flightobj) {
        try {
            return ResponseEntity.ok(flightServiceImpl.addFlight(flightobj));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{flightid}")
    public ResponseEntity<FlightsDTO> updateFlight(@PathVariable Long flightid, FlightsDTO flight) {
        try {
            flightServiceImpl.updateFLight(flightid, flight);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
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
