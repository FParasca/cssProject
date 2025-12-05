package pt.ul.fc.css.urbanwheels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.urbanwheels.entities.Client;
import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    @Query("SELECT c FROM Client c JOIN c.trips t WHERE t.startTime >= :date GROUP BY c ORDER BY COUNT(t) DESC")
    List<Client> findTopClientsByTripsSince(@Param("date") LocalDateTime date);

}