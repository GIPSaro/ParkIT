package giorgiaipsaropassione.ParkIT.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnnualCardDTO(
        @NotNull UUID userId,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @Positive Double price
) {
}
