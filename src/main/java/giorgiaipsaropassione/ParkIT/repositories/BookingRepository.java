package giorgiaipsaropassione.ParkIT.repositories;

import giorgiaipsaropassione.ParkIT.entities.Booking;
import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {


    Optional<Booking> findByParkingSlotAndEndTimeIsNull(ParkingSlot parkingSlot);
}
