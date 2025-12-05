package pt.ul.fc.css.urbanwheels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.urbanwheels.dto.AdminDTO;
import pt.ul.fc.css.urbanwheels.dto.ClientDTO;
import pt.ul.fc.css.urbanwheels.dto.UserDTO;
import pt.ul.fc.css.urbanwheels.services.AdminService;
import pt.ul.fc.css.urbanwheels.services.ClientService;
import pt.ul.fc.css.urbanwheels.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Users_API")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    @Operation(summary = "Get_all_users", description = "Returns a list of all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get_user_details", description = "Returns details of a user.")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable("email") String email) {
        UserDTO userDto = userService.getUserDetails(email);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }
}