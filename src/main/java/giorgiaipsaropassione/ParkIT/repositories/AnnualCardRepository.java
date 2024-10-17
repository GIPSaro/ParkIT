package giorgiaipsaropassione.ParkIT.repositories;


import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnnualCardRepository extends JpaRepository<AnnualCard, UUID> {
}
