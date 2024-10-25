package giorgiaipsaropassione.ParkIT.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSlotDTO {
    private String name;
    private String location;
    private String address;
    private double latitude;
    private double longitude;
}