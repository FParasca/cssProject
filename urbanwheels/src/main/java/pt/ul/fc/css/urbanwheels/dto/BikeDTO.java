package pt.ul.fc.css.urbanwheels.dto;

import pt.ul.fc.css.urbanwheels.entities.BikeStatus;
import pt.ul.fc.css.urbanwheels.entities.Station;

import java.util.List;

public class BikeDTO {
    private Long id;
    private String modelo;
    private String bikeStatus;
    private Long currentStationId;


    public BikeDTO() {}

    public BikeDTO(Long id, String modelo, String bikeStatus, Long currentStationId) {
        this.id = id;
        this.modelo = modelo;
        this.bikeStatus = bikeStatus;
        this.currentStationId = currentStationId;

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
    public String getBikeStatus() {
        return bikeStatus;
    }
    public void setBikeStatus(String bikeStatus) {
        this.bikeStatus = bikeStatus;
    }
    public Long getCurrentStationId() {
        return currentStationId;
    }
    public void setCurrentStationId(Long currentStationId) {
        this.currentStationId = currentStationId;
    }


}
