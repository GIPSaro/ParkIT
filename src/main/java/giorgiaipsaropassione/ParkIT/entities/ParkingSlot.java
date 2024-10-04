package giorgiaipsaropassione.ParkIT.entities;

import giorgiaipsaropassione.ParkIT.enums.ParkingSlotStatus;
import jakarta.persistence.*;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parking_slots")
public class ParkingSlot {

    @Id
    @GeneratedValue
    private UUID id;
    private String location;

    @Enumerated(EnumType.STRING)
    private ParkingSlotStatus status;

    public ParkingSlot(String location, ParkingSlotStatus status) {
        this.location = location;
        this.status = status;
    }
}
