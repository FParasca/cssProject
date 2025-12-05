package pt.ul.fc.css.urbanwheels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.urbanwheels.dto.TripDTO;
import pt.ul.fc.css.urbanwheels.services.TripService;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@Tag(name = "Trips", description = "Trips_API")
public class TripController {
    @Autowired
    private TripService tripService;

    @PostMapping("/endtrip")
    @Operation(summary = "End Trip", description = "Ends a trip")
    public ResponseEntity<TripDTO> returnBike(@RequestParam Long bikeId, @RequestParam Long stationId) {
        try {
            TripDTO tripDto = tripService.returnBike(bikeId, stationId);
            return ResponseEntity.ok(tripDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }
    @PostMapping("/starttrip")
    @Operation(summary = "Start Trip", description = "Start a trip")
    public ResponseEntity<TripDTO> takeBike(@RequestParam Long bikeId, @RequestParam Long stationId , @RequestParam long clientID) {
        try {
            TripDTO tripDto = tripService.takeBike(bikeId, stationId,clientID);
            return ResponseEntity.ok(tripDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }
    @GetMapping("/search")
    @Operation(summary = "Find Trips by Route", description = "Returns all trips that started at station A and ended at station B.")
    public ResponseEntity<List<TripDTO>> getTripsByRoute(
            @RequestParam Long startStationId,
            @RequestParam Long endStationId) {

        List<TripDTO> trips = tripService.getTripsByRoute(startStationId, endStationId);
        return ResponseEntity.ok(trips);
    }
}