package pt.ul.fc.css.urbanwheels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.urbanwheels.entities.Admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);


    @Query("SELECT a FROM Admin a JOIN a.maintenanceTasks m WHERE m.date >= :date GROUP BY a ORDER BY COUNT(m) DESC")
    List<Admin> findTopAdminsByMaintenanceSince(@Param("date") LocalDateTime date);

}