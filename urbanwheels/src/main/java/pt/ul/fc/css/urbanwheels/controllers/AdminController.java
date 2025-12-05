package pt.ul.fc.css.urbanwheels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.urbanwheels.dto.AdminDTO;
import pt.ul.fc.css.urbanwheels.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@Tag(name = "Admins", description = "Admin_API")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    @Operation(summary = "Admin_Register", description = "Creates a new Administrator")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody AdminDTO adminDto) {
        try {
            AdminDTO responseDto = adminService.createAdmin(adminDto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/top-maintenance")
    @Operation(summary = "Top Admins (Maintenance)", description = "Returns administrators with most maintenance operations in the last month.")
    public ResponseEntity<List<AdminDTO>> getTopAdmins() {
        return ResponseEntity.ok(adminService.getTopAdminsLastMonth());
    }
}