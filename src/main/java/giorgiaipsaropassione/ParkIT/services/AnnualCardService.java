package giorgiaipsaropassione.ParkIT.services;

import giorgiaipsaropassione.ParkIT.DTO.AnnualCardDTO;
import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.repositories.AnnualCardRepository;
import giorgiaipsaropassione.ParkIT.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AnnualCardService {

    @Autowired
    private AnnualCardRepository annualCardRepository;

    @Autowired
    private UsersService userService;
    @Autowired
    private UsersRepository userRepository;


    public AnnualCard purchaseCardForUser(UUID userId, AnnualCardDTO body) {
        System.out.println("Attempting to purchase card for user: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AnnualCard existingCard = annualCardRepository.findByUserId(userId)
                .orElse(null);

        if (existingCard != null && existingCard.getEndDate().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("L'utente ha già una tessera annuale attiva.");
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusYears(1);
        Double price = body.getPrice();

        AnnualCard annualCard = new AnnualCard(user, startDate, endDate, price);
        System.out.println("Saving new annual card for user: " + userId);
        annualCardRepository.save(annualCard);

        System.out.println("Impostazione del flag hasAnnualCard su true per l'utente: " + userId);
        user.setHasAnnualCard(true);
        userRepository.save(user);

        return annualCard;
    }




    public AnnualCard getAnnualCardById(UUID annualCardId) {
        return annualCardRepository.findById(annualCardId)
                .orElseThrow(() -> new IllegalArgumentException("Annual card not found"));
    }


}