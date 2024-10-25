package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.entities.PaymentMethod;
import giorgiaipsaropassione.ParkIT.services.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    public ResponseEntity<PaymentMethod> createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        PaymentMethod created = paymentMethodService.createPaymentMethod(paymentMethod);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable UUID id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);
        return paymentMethod != null ? new ResponseEntity<>(paymentMethod, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable UUID id) {
        paymentMethodService.deletePaymentMethod(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/secure-page")
    public ResponseEntity<String> getSecurePage(HttpServletResponse response) {
        // Genera un nonce
        String nonce = UUID.randomUUID().toString();

        // Imposta l'intestazione CSP
        response.setHeader("Content-Security-Policy",
                "script-src 'self' 'nonce-" + nonce + "' https://www.paypal.com;");

        return new ResponseEntity<>("Secure page with nonce: " + nonce, HttpStatus.OK);
    }
}
