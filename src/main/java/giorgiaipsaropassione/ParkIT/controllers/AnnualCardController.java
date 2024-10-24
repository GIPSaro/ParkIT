package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.AnnualCardDTO;
import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import giorgiaipsaropassione.ParkIT.services.AnnualCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/annualCards")
public class AnnualCardController {

    @Autowired
    private AnnualCardService annualCardService;

    // Endpoint per acquistare la tessera annuale
    @PostMapping("/purchase/{userId}")
    public ResponseEntity<AnnualCard> purchaseCard(@PathVariable UUID userId, @RequestBody AnnualCardDTO body) {
        AnnualCard purchasedCard = annualCardService.purchaseCardForUser(userId, body);
        return ResponseEntity.ok(purchasedCard);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public AnnualCardDTO getAnnualCard(@PathVariable UUID userId) {
        AnnualCard annualCard = annualCardService.getAnnualCardForUser(userId);
        return new AnnualCardDTO(annualCard.getStartDate(), annualCard.getEndDate(), annualCard.getPrice());
    }

}
