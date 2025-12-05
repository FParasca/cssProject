package pt.ul.fc.css.urbanwheels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.urbanwheels.entities.Station;

import java.util.List;
import java.util.Optional;


@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Optional<Station> findByName(String name);

    @Query("SELECT s FROM Station s JOIN s.bikesAssociated b GROUP BY s ORDER BY COUNT(b) DESC")
    List<Station> findStationsWithMostBikes();
}