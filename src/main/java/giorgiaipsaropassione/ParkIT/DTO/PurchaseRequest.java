package giorgiaipsaropassione.ParkIT.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseRequest {
    private String email;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double amount;


}
