package giorgiaipsaropassione.ParkIT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_slot_id", nullable = false)
    private ParkingSlot parkingSlot;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int remainingFreeParkings = 10;

    public Subscription(User user, LocalDateTime startDate, LocalDateTime endDate, double price) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public void useFreeParking() {
        if (remainingFreeParkings > 0) {
            remainingFreeParkings--;
        } else {
            throw new IllegalStateException("Hai terminato i parcheggi gratuiti");
        }
    }
}
