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

        LocalDateTime startDate = body.getStartDate();
        LocalDateTime endDate = body.getEndDate();
        Double price = body.getPrice();

        AnnualCard annualCard = new AnnualCard(user, startDate, endDate, price);

        // Imposta l'AnnualCard all'utente
        user.setAnnualCard(annualCard);
        user.setHasAnnualCard(true);

        userRepository.save(user);
        return annualCardRepository.save(annualCard);
    }

}
