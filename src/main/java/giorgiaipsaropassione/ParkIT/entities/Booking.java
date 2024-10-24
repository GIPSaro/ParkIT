package giorgiaipsaropassione.ParkIT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import giorgiaipsaropassione.ParkIT.enums.BookingStatus;

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

    @Enumerated(EnumType.STRING) // Salviamo il valore dell'enum come stringa nel database
    private BookingStatus status = BookingStatus.FREE;

    public Booking(User user, ParkingSlot parkingSlot, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status) {
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
}
