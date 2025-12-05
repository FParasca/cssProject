package pt.ul.fc.css.urbanwheels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.urbanwheels.dto.StationDTO;
import pt.ul.fc.css.urbanwheels.services.StationService;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@Tag(name = "Stations", description = "Stations_API")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping
    @Operation(summary = "Station_Register", description = "Creates a new Station")
    public ResponseEntity<StationDTO> registerStation(@RequestBody StationDTO stationDto) {
        try {
            StationDTO responseDto = stationService.createStation(stationDto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get_all_stations", description = "Returns a list of all stations")
    public ResponseEntity<List<StationDTO>> getAllStations() {
        List<StationDTO> stations = stationService.getAllStations();
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get_station", description = "Returns the details of a station")
    public ResponseEntity<StationDTO> getStationDetails(@PathVariable("name") String name) {
        StationDTO stationDTO = stationService.getStationDetails(name);
        if (stationDTO != null) {
            return ResponseEntity.ok(stationDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/most-bikes")
    @Operation(summary = "List Stations by Bikes", description = "Returns a list of stations")
    public ResponseEntity<List<StationDTO>> getStationsWithMostBikes() {
        List<StationDTO> stations = stationService.getStationsWithMostBikes();
        return ResponseEntity.ok(stations);
    }
    @GetMapping("/favorite/{email}")
    @Operation(summary = "User's Favorite Station", description = "Returns the station most used by a specific user.")
    public ResponseEntity<StationDTO> getFavoriteStation(@PathVariable("email") String email) {
        StationDTO stationDTO = stationService.getMostUsedStationByUser(email);

        if (stationDTO != null) {
            return ResponseEntity.ok(stationDTO);
        }
        return ResponseEntity.notFound().build();
    }
}