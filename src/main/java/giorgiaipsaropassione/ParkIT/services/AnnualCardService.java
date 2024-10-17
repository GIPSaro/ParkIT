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
    private UsersRepository usersRepository;

    public AnnualCard purchaseAnnualCard(AnnualCardDTO annualCardDTO) {
        UUID userId = annualCardDTO.userId();
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusYears(1);

        AnnualCard annualCard = new AnnualCard(user, startDate, endDate, annualCardDTO.price());
        return annualCardRepository.save(annualCard);
    }
}