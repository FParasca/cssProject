package pt.ul.fc.css.urbanwheels.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.urbanwheels.dto.BikeDTO;
import pt.ul.fc.css.urbanwheels.dto.MaintenanceDTO;
import pt.ul.fc.css.urbanwheels.services.BikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/bikes")
@Api(value = "Bike Management API", tags = "Bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    //Caso de Uso D
    @PostMapping
    @ApiOperation(value = "Add a new bike to the station", notes = "Associates the bike with the initial station")
    public ResponseEntity<BikeDTO> createBike(@RequestBody BikeDTO bikeDTO) {
        try{
            BikeDTO newBike = bikeService.createBike(bikeDTO);
            return new ResponseEntity<>(newBike, HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    //Caso de Uso E
    @GetMapping
    @ApiOperation(value = "List all bikes", notes = "Optionally filter bikes by their current status (AVAILABLE, IN_USE, etc.).")
    public ResponseEntity<List<BikeDTO>> listBikes(@RequestParam(required = false) String status) {
        List<BikeDTO> bikes = bikeService.listBikesByStatus(status);
        return ResponseEntity.ok(bikes);
    }
    //Caso de Uso F
    @PatchMapping("/{bikeId}/status")
    @ApiOperation(value = "Update bike status", notes = "Changes the bike's status (e.g., to MAINTENANCE or AVAILABLE). Requires stationId if setting to AVAILABLE.")
    public ResponseEntity<BikeDTO> updateBikeStatus(@PathVariable Long bikeId, @RequestBody String newStatus) {
        try {
            BikeDTO responseDto = bikeService.updateBikeStatus(bikeId, newStatus);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //Caso de Uso G
    @PostMapping("/{bikeId}/maintenance")
    @ApiOperation(value = "Register maintenance operation", notes = "Records a new maintenance event for a bike. Changes bike status if it was AVAILABLE.")
    public ResponseEntity<MaintenanceDTO> registerMaintenance(@PathVariable Long bikeId, @RequestBody MaintenanceDTO maintenanceDTO) {
        try {
            MaintenanceDTO responseDto = bikeService.registerMaintenance(bikeId, maintenanceDTO);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/most-maintenance")
    @Operation(summary = "Bike with Most Maintenance", description = "Returns the bike that spent the most time in maintenance in the last year.")
    public ResponseEntity<BikeDTO> getBikeWithMostMaintenance() {
        BikeDTO bikeDTO = bikeService.getBikeWithMostMaintenanceLastYear();

        if (bikeDTO != null) {
            return ResponseEntity.ok(bikeDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
