package pt.ul.fc.css.weatherwise.business.dto;

import java.time.LocalDateTime;

public class WeatherDataDTO {

    private String locationName;
    private String condition;
    private double temperature;
    private LocalDateTime timestamp;

    public WeatherDataDTO() {
    }
    public WeatherDataDTO(String locationName, String condition, double temperature, LocalDateTime timestamp) {
        this.locationName = locationName;
        this.condition = condition;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }
    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}