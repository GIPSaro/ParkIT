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

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Controlla se l'utente ha già una tessera annuale attiva
        AnnualCard existingCard = annualCardRepository.findByUserId(userId)
                .orElse(null);

        // Se c'è una tessera esistente e la sua data di fine è futura, non consentire l'acquisto
        if (existingCard != null && existingCard.getEndDate().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("L'utente ha già una tessera annuale attiva.");
        }


        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusYears(1);
        Double price = body.getPrice();

        // Crea una nuova tessera annuale
        AnnualCard annualCard = new AnnualCard(user, startDate, endDate, price);


        annualCard.purchaseSubscription(user, price);

        // Salva l'utente e la nuova tessera annuale
        userRepository.save(user); // Salva l'utente
        return annualCardRepository.save(annualCard); // Salva la nuova tessera
    }



    public AnnualCard getAnnualCardForUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return annualCardRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Annual card not found for user"));
    }

}