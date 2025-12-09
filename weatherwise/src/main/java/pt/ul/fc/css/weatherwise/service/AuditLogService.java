package pt.ul.fc.css.weatherwise.service;

import pt.ul.fc.css.weatherwise.business.dto.AuditLogDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditLogService {
    public List<AuditLogDTO> getAuditHistory() {
        // TODO
        return null;
    }
    public long countLogsByLocation(String location) {
        // TODO
        return 0;
    }
}
