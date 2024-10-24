package giorgiaipsaropassione.ParkIT.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import giorgiaipsaropassione.ParkIT.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDTO {
    private UUID userId;
    private UUID parkingSlotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus status;
}
