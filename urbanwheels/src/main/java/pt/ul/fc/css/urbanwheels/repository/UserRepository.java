package pt.ul.fc.css.urbanwheels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ul.fc.css.urbanwheels.entities.User;
import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);

        List<User> findByName(String name);

        boolean existsByEmail(String email);
}