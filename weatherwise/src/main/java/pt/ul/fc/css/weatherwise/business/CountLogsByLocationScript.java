package pt.ul.fc.css.weatherwise.business;

import pt.ul.fc.css.weatherwise.business.exception.ApplicationException;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.AuditLogRowDataGateway;

public class CountLogsByLocationScript {
    private final AuditLogRowDataGateway auditLogRowDataGateway;

    public CountLogsByLocationScript (AuditLogRowDataGateway auditLogRowDataGateway) {
        this.auditLogRowDataGateway = auditLogRowDataGateway;
    }

    public int CountLogsByLocation(String location) throws ApplicationException {
        if(!isFilled(location)){
            throw new ApplicationException("Location must be filled.");
        }
        try {
            return auditLogRowDataGateway.getLogsByLocation(location);
        } catch (PersistenceException e) {
            throw new ApplicationException("Error in database searching: " + location, e);
        }

    }

    private boolean isFilled(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
