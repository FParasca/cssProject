package pt.ul.fc.css.weatherwise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.weatherwise.business.dto.AuditLogDTO;
import pt.ul.fc.css.weatherwise.service.AuditLogService;
import pt.ul.fc.css.weatherwise.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private UserService userService;

    @GetMapping("/history")
    public ResponseEntity<List<AuditLogDTO>> getAuditHistory() {
        return ResponseEntity.ok(auditLogService.getAuditHistory());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countLogsByLocation(@RequestParam String location) {
        return ResponseEntity.ok(auditLogService.countLogsByLocation(location));
    }

    @GetMapping("/top-user")
    public ResponseEntity<String> getTopUserByLocation(@RequestParam String location) {
        return ResponseEntity.ok(userService.findTopUserByLocation(location));
    }
}
