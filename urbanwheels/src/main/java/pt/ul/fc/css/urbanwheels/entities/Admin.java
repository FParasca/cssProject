package pt.ul.fc.css.urbanwheels.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends User {

    @OneToMany(mappedBy = "admin")
    private List<Maintenance> maintenanceTasks = new ArrayList<>();

    public Admin() {}

    public Admin(String email, String name) {
        super(email, name);
        }
    public void addMaintenance(Maintenance maintenance) {
        this.maintenanceTasks.add(maintenance);
    }

    public List<Maintenance> getMaintenanceTasks() {
        return maintenanceTasks;
    }
}