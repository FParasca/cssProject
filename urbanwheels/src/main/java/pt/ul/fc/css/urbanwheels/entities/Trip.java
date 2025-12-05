package pt.ul.fc.css.urbanwheels.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToOne(optional = false)
    @JoinColumn(name = "start_station_id")
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id")
    private Station endStation;

    public Trip() {}


    public Trip(Bike bike, Client client, Station startStation, LocalDateTime startTime) {
        this.bike = bike;
        this.client = client;
        this.startStation = startStation;
        this.startTime = startTime;
    }


    public Long getId() { return id; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Bike getBike() { return bike; }
    public Station getStartStation() { return startStation; }
    public Station getEndStation() { return endStation; }
    public Client getClient() { return client; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setEndStation(Station endStation) { this.endStation = endStation; }
    public void setBike(Bike bike) { this.bike = bike; }
    public void setStartStation(Station startStation) { this.startStation = startStation; }
    public void setClient(Client client) { this.client = client; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
}