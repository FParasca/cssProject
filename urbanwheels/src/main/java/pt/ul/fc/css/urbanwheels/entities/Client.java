package pt.ul.fc.css.urbanwheels.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private SubscriptionType subscription;


    @OneToMany(mappedBy = "client")
    private List<Trip> trips = new ArrayList<>();


    protected Client() {}

    public Client(String email, String name, SubscriptionType subscription) {
        super(email, name);
        this.subscription = subscription;
    }

    public SubscriptionType getSubscription() {
        return subscription;

    }

    public void setSubscription(SubscriptionType subscription) {
        this.subscription = subscription;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public List<Trip> getTrips() {
        return trips;
    }

}