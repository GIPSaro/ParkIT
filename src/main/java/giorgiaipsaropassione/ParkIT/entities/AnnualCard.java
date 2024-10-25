package giorgiaipsaropassione.ParkIT.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "annualCards")
public class AnnualCard {
    @Id
    @GeneratedValue
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_slot_id", nullable = true)
    private ParkingSlot parkingSlot;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = true)
    private Double price;

    @Column(nullable = false)
    private int remainingFreeParkings = 0;

    @Column(nullable = false)
    private boolean active = false;

    public AnnualCard(User user, LocalDateTime startDate, LocalDateTime endDate, Double price) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.remainingFreeParkings = 10;
    }

    public void useFreeParking() {
        if (remainingFreeParkings > 0) {
            remainingFreeParkings--;
        } else {
            throw new IllegalStateException("Hai terminato i parcheggi gratuiti");
        }
    }

    public void assignParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }




}
