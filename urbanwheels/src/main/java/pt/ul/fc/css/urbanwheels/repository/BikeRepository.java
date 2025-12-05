package pt.ul.fc.css.urbanwheels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.urbanwheels.entities.Bike;
import pt.ul.fc.css.urbanwheels.entities.BikeStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

    List<Bike> findByStatus(BikeStatus status);

    @Query("SELECT b FROM Bike b JOIN b.maintenanceHistory m WHERE m.date >= :date GROUP BY b ORDER BY SUM(m.timeInMaintenance) DESC")
    List<Bike> findBikesWithMostMaintenanceTimeSince(@Param("date") LocalDateTime date);
}