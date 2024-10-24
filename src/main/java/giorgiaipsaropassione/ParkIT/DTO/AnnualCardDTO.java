package giorgiaipsaropassione.ParkIT.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

public class AnnualCardDTO {
    @NotNull
    private Double price;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    public AnnualCardDTO(LocalDateTime startDate, LocalDateTime endDate, Double price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
}
