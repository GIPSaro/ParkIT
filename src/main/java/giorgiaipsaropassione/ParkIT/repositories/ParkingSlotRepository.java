package giorgiaipsaropassione.ParkIT.repositories;


import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import giorgiaipsaropassione.ParkIT.enums.ParkingSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, UUID> {
    ParkingSlot findByIdAndStatus(UUID id, ParkingSlotStatus status);
    boolean existsByLocation(String location);
}
