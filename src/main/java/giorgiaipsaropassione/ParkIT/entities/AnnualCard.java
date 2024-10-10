package giorgiaipsaropassione.ParkIT.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "annualCards")
public class AnnualCard {
    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_slot_id", nullable = true)
    private ParkingSlot parkingSlot;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = true) //solo se l'utente acquista la tessera al momento della registrazione
    private Double price;

    @Column(nullable = false)
    private int remainingFreeParkings = 0;

    public AnnualCard(User user, LocalDateTime startDate, LocalDateTime endDate, Double price) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price; // Prezzo può essere null se l'abbonamento non viene acquistato
        this.remainingFreeParkings = 10; // 10 parcheggi gratuiti se l'abbonamento è attivo
    }

    public void useFreeParking() {
        if (remainingFreeParkings > 0) {
            remainingFreeParkings--;
        } else {
            throw new IllegalStateException("Hai terminato i parcheggi gratuiti");
        }
    }
    public void assignParkingSlot(ParkingSlot parkingSlot){
        this.parkingSlot = parkingSlot;
    }
    //metodo per acquistare l'annualCard e quindi abilitare i parcheggi gratuiti
    public void purchaseSubscription(double price){
        if(this.price == null){
            this.price= price;
            this.remainingFreeParkings= 10;
        }else{
            throw new IllegalStateException("Hai gia acquistato una tessera ");
        }
    }
}
