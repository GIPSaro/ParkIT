package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.AnnualCardDTO;
import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import giorgiaipsaropassione.ParkIT.services.AnnualCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annualCards")
public class AnnualCardController {

    @Autowired
    private AnnualCardService annualCardService;

    @PostMapping("/purchase")
    public ResponseEntity<AnnualCard> purchaseAnnualCard(@RequestBody AnnualCardDTO annualCardDTO) {
        try {
            AnnualCard annualCard = annualCardService.purchaseAnnualCard(annualCardDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(annualCard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
