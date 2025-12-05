package pt.ul.fc.css.urbanwheels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.urbanwheels.entities.Station;
import pt.ul.fc.css.urbanwheels.entities.Trip;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByBikeIdAndEndTimeIsNull(Long bikeId);

    @Query("SELECT t.startStation FROM Trip t WHERE t.client.email = :email GROUP BY t.startStation ORDER BY COUNT(t) DESC")
    List<Station> findFavoriteStationsByClientEmail(@Param("email") String email);

    List<Trip> findByStartStationIdAndEndStationId(Long startStationId, Long endStationId);
}
