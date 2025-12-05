package pt.ul.fc.css.urbanwheels.dto;

import java.time.LocalDateTime;

public class TripDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bikeModel;
    private String startStationName;
    private String endStationName;

    public TripDTO() {}

    public TripDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, String bikeModel, String startStationName, String endStationName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bikeModel = bikeModel;
        this.startStationName = startStationName;
        this.endStationName = endStationName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getBikeModel() { return bikeModel; }
    public void setBikeModel(String bikeModel) { this.bikeModel = bikeModel; }
    public String getStartStationName() { return startStationName; }
    public void setStartStationName(String startStationName) { this.startStationName = startStationName; }
    public String getEndStationName() { return endStationName; }
    public void setEndStationName(String endStationName) { this.endStationName = endStationName; }
}