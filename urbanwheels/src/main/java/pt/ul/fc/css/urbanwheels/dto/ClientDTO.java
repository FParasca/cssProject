package pt.ul.fc.css.urbanwheels.dto;

import pt.ul.fc.css.urbanwheels.entities.SubscriptionType;
import java.util.ArrayList;
import java.util.List;

public class ClientDTO extends UserDTO {

    private SubscriptionType subscription;
    private List<TripDTO> trips = new ArrayList<>();    
    
    public ClientDTO() {
        super();
    }



    public ClientDTO(Long id, String email, String name, SubscriptionType subscription, List<TripDTO> trips) {
        super(id, name, email);
        this.subscription = subscription;
        this.trips = trips != null ? trips : new ArrayList<>();
    }

    public SubscriptionType getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionType subscription) {
        this.subscription = subscription;
    }

    public List<TripDTO> getTrips() { return trips; }

    public void setTrips(List<TripDTO> trips) { this.trips = trips; }
    }
