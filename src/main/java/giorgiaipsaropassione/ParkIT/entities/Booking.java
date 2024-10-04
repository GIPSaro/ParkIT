package giorgiaipsaropassione.ParkIT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parkingSlotId", nullable = false)
    private ParkingSlot parkingSlot;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Booking(User user, ParkingSlot parkingSlot, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
