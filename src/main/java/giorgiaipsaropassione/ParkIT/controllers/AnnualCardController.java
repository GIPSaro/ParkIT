package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.AnnualCardDTO;
import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.services.AnnualCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        System.out.println("Request to purchase card for user ID: " + userId);
        AnnualCard purchasedCard = annualCardService.purchaseCardForUser(userId, body);
        return ResponseEntity.ok(purchasedCard);
    }

    @GetMapping("/{annualCardId}")
    public ResponseEntity<AnnualCardDTO> getAnnualCard(@PathVariable UUID annualCardId) {
        AnnualCard annualCard = annualCardService.getAnnualCardById(annualCardId);
        if (annualCard == null) {
            return ResponseEntity.notFound().build();
        }


        User user = annualCard.getUser();


        AnnualCardDTO dto = new AnnualCardDTO(
                annualCard.getId(),
                annualCard.getStartDate(),
                annualCard.getEndDate(),
                annualCard.getPrice(),
                user != null ? user.getName() : null,
                user != null ? user.getSurname() : null
        );

        return ResponseEntity.ok(dto);
    }



}
