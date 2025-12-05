package pt.ul.fc.css.urbanwheels.dto;

import pt.ul.fc.css.urbanwheels.entities.Bike;
import pt.ul.fc.css.urbanwheels.entities.Station;
import java.util.List;
import java.util.ArrayList;

public class StationDTO {
    private Long id;
    private List<BikeDTO> bikesAssociated = new ArrayList<>();
    private String name;
    private double latitude;
    private double longitude;
    private int capacity;

    public StationDTO() {}

    public StationDTO(Long id, String name, Double latitude, Double longitude, int capacity) {
        this.id = id;
        this.bikesAssociated = bikesAssociated;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BikeDTO> getBikesAssociated() {
        return bikesAssociated;
    }

    public void setBikesAssociated(List<BikeDTO> bikesAssociated){
        this.bikesAssociated = bikesAssociated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
