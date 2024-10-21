package giorgiaipsaropassione.ParkIT.repositories;


import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import giorgiaipsaropassione.ParkIT.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface AnnualCardRepository extends JpaRepository<AnnualCard, UUID> {
    boolean existsByUser(User user);

}
