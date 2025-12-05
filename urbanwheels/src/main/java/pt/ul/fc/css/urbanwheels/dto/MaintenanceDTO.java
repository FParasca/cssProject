package pt.ul.fc.css.urbanwheels.dto;

import java.time.LocalDateTime;

public class MaintenanceDTO {
    private Long id;
    private Long AdminId;
    private Long BikeId;
    private String description;
    private double cost;
    private LocalDateTime date;


    public MaintenanceDTO(){}

    public MaintenanceDTO(Long id, Long AdminId, String description, double cost, LocalDateTime date, Long BikeId) {
        this.id = id;
        this.AdminId = AdminId;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.BikeId = BikeId;

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Long getBikeId() {
        return BikeId;
    }
    public void setBikeId(Long bikeId) {
        BikeId = bikeId;
    }
    public Long getAdminId() {
        return AdminId;
    }
    public void setAdminId(Long adminId) {
        AdminId = adminId;
    }
}


