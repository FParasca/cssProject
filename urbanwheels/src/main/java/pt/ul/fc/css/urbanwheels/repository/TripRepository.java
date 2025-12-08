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


    List<Trip> findByStartStationIdAndEndStationId(Long startStationId, Long endStationId);

    @Query("SELECT t.startStation FROM Trip t WHERE t.client.email = :email GROUP BY t.startStation ORDER BY COUNT(t) DESC")
    List<Station> findTopStartStationsByUser(@Param("email") String email);
    
    @Query("SELECT t.endStation FROM Trip t WHERE t.client.email = :email AND t.endStation IS NOT NULL GROUP BY t.endStation ORDER BY COUNT(t) DESC")
    List<Station> findTopEndStationsByUser(@Param("email") String email);

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.startStation.id = :stationId AND t.client.email = :email")
    long countByStartStationAndUser(@Param("stationId") Long stationId, @Param("email") String email);

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.endStation.id = :stationId AND t.client.email = :email")
    long countByEndStationAndUser(@Param("stationId") Long stationId, @Param("email") String email);
}
