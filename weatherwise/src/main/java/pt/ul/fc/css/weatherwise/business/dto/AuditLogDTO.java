package pt.ul.fc.css.weatherwise.business.dto;

import java.time.LocalDateTime;

public class AuditLogDTO{

    private String author;
    private String locationName;
    private LocalDateTime timestamp;

    public AuditLogDTO() {
    }
    public AuditLogDTO(String author, String locationName, LocalDateTime timestamp) {
        this.author = author;
        this.locationName = locationName;
        this.timestamp = timestamp;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}