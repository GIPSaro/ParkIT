package giorgiaipsaropassione.ParkIT.services;

import giorgiaipsaropassione.ParkIT.entities.PaymentMethod;
import giorgiaipsaropassione.ParkIT.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodById(UUID id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }

    public void deletePaymentMethod(UUID id) {
        paymentMethodRepository.deleteById(id);
    }


}
