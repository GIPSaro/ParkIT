package giorgiaipsaropassione.ParkIT.entities;

import com.cloudinary.Coordinates;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String location;
    private String address;
    private double latitude;
    private double longitude;




    @Enumerated(EnumType.STRING)
    private ParkingSlotStatus status;

    public ParkingSlot(String location, ParkingSlotStatus status) {
        this.location = location;
        this.status = status;
    }


}
