package pt.ul.fc.css.urbanwheels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.urbanwheels.dto.ClientDTO;
import pt.ul.fc.css.urbanwheels.services.ClientService;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients", description = "Clients_API")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    @Operation(summary = "Client_Register", description = "Creates a new Client")
    public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientDTO clientDto) {
        try {
            ClientDTO responseDto = clientService.createClient(clientDto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/top-travelers")
    @Operation(summary = "Top Travelers Last Month", description = "Returns users most trips last month.")
    public ResponseEntity<List<ClientDTO>> getTopTravelers() {
        return ResponseEntity.ok(clientService.getTopClientsLastMonth());
    }
}