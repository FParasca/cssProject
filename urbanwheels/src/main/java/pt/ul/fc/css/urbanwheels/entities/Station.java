package pt.ul.fc.css.urbanwheels.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "currentStation")
    private List<Bike> bikesAssociated = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private int capacity;

    public Station() {
    }

    public Station(Long id, String name, double latitude, double longitude, int capacity) {
        this.id = id;
        this.bikesAssociated = new ArrayList<>();
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

    public void setBikesAssociated(List<Bike> bikesAtStation) {
        this.bikesAssociated = bikesAtStation;
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

    public List<Bike> getBikesAssociated() {
        return bikesAssociated;
    }

    public void addBike(Bike bike) {
        bikesAssociated.add(bike);
        bike.setCurrentStation(this);
    }

    public void removeBike(Bike bike) {
        bikesAssociated.remove(bike);
    }
}