package giorgiaipsaropassione.ParkIT.repositories;

import giorgiaipsaropassione.ParkIT.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Page<User> findByHasAnnualCard(Boolean hasAnnualCard, Pageable pageable);
    Page<User> findByDateOfRegisterAfter(LocalDateTime start, Pageable pageable);
}
