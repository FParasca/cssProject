package pt.ul.fc.css.urbanwheels.dto;

import java.util.ArrayList;
import java.util.List;

public class AdminDTO extends UserDTO {
    
    private List<MaintenanceDTO> maintenanceTasks = new ArrayList<>();

    public AdminDTO() {
        super();
    }

    public AdminDTO(Long id, String email, String name, List<MaintenanceDTO> maintenanceTasks) {
        super(id, name, email);
        this.maintenanceTasks = maintenanceTasks != null ? maintenanceTasks : new ArrayList<>();
    }

    public List<MaintenanceDTO> getMaintenanceTasks() {
        return maintenanceTasks;
    }

    public void setMaintenanceTasks(List<MaintenanceDTO> maintenanceTasks) {
        this.maintenanceTasks = maintenanceTasks;
    }
}