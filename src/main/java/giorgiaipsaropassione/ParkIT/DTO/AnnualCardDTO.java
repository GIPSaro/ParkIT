package giorgiaipsaropassione.ParkIT.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnnualCardDTO(
        UUID userId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Double price
) {
}
