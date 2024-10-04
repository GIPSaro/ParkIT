package giorgiaipsaropassione.ParkIT.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parkingSlot_id", nullable = false)
    private ParkingSlot parkingSlot;


    private LocalDateTime startTime;
    private Integer duration;
    private Double price;

    public Ticket(User user, ParkingSlot parkingSlot, LocalDateTime startTime, int duration, double price) {
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.startTime = startTime;
        this.duration = duration;
        this.price = price;
    }

}
