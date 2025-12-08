package pt.ul.fc.css.weatherwise.business;

import pt.ul.fc.css.weatherwise.business.exception.ApplicationException;
import pt.ul.fc.css.weatherwise.dataaccess.exception.PersistenceException;
import pt.ul.fc.css.weatherwise.dataaccess.rdgw.AuditLogRowDataGateway;

public class FindTopUserByLocationScript {
    private final AuditLogRowDataGateway auditGateway;

    public FindTopUserByLocationScript(AuditLogRowDataGateway auditLogRowDataGateway) {
        this.auditGateway = auditLogRowDataGateway;
    }

    public String findTopUserByLocation(String location) throws ApplicationException {
        if(!isFilled(location)){
            throw new ApplicationException("Location must be filled.");
        }
        try {
            String topUser = auditGateway.getTopUserByLocation(location);
            if(topUser == null){
                return "No user found";
            }
            return topUser;
        } catch (PersistenceException e) {
            throw new ApplicationException("Error in database searching: " + location, e);
        }
    }

    private boolean isFilled(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
