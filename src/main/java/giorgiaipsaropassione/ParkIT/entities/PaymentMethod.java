package giorgiaipsaropassione.ParkIT.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.UUID;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor

public class PaymentMethod {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private String type;
    private String details;


}
