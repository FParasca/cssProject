package pt.ul.fc.css.weatherwise.business;

import pt.ul.fc.css.weatherwise.business.dto.AuditLogDTO;
import pt.ul.fc.css.weatherwise.business.exception.ApplicationException;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.AuditLogRowDataGateway;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;


import java.util.List;

public class GetAuditHistoryScript {

    private final AuditLogRowDataGateway auditLogGateway;

    public GetAuditHistoryScript(AuditLogRowDataGateway auditLogGateway) {
        this.auditLogGateway = auditLogGateway;
    }

    public List<AuditLogDTO> getAuditHistory() throws ApplicationException{
        try {
            return auditLogGateway.findAll();
        } catch (PersistenceException e) {
            throw new ApplicationException("Error in database");
        }
    }
}