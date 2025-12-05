package pt.ul.fc.css.urbanwheels.entities;
import jakarta.persistence.*;
import pt.ul.fc.css.urbanwheels.entities.BikeStatus;
import pt.ul.fc.css.urbanwheels.entities.Station;

import java.util.List;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BikeStatus status;

    @ManyToOne
    @JoinColumn(name = "current_station_id")
    private Station currentStation;

    @OneToMany(mappedBy = "bike")
    private List<Maintenance> maintenanceHistory;

    public Bike() {
    }

    public Bike(String modelo, BikeStatus status, Station currentStation ) {
        this.modelo = modelo;
        this.status = status;
        this.currentStation = currentStation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public BikeStatus getStatus() {
        return status;
    }

    public void setStatus(BikeStatus status) {
        this.status = status;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(Station currentStation) {
        this.currentStation = currentStation;
    }

    public List<Maintenance> getMaintenanceHistory() {
        return maintenanceHistory;
    }
    public void setMaintenanceHistory(List<Maintenance> maintenanceHistory) {
        this.maintenanceHistory = maintenanceHistory;
    }

}