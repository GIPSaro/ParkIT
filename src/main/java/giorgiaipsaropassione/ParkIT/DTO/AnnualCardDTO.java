package giorgiaipsaropassione.ParkIT.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

public class AnnualCardDTO {
    @NotNull
    private UUID id;
    @NotNull
    private Double price;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;

    private String userName;
    private String userSurname;
    public AnnualCardDTO(UUID id, LocalDateTime startDate, LocalDateTime endDate, Double price, String userName, String userSurname) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.userName = userName;
        this.userSurname =userSurname;
    }
}
