package giorgiaipsaropassione.ParkIT.repositories;
import giorgiaipsaropassione.ParkIT.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

}
