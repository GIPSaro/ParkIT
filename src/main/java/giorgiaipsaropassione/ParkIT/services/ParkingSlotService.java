package giorgiaipsaropassione.ParkIT.services;

import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import giorgiaipsaropassione.ParkIT.enums.ParkingSlotStatus;
import giorgiaipsaropassione.ParkIT.repositories.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }

    public ParkingSlot getParkingSlotById(UUID id) {
        return parkingSlotRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking slot not found"));
    }

    public Optional<ParkingSlot> getParkingSlotByIdAndStatus(UUID id, ParkingSlotStatus status) {
        return Optional.ofNullable(parkingSlotRepository.findByIdAndStatus(id, status));
    }

    public ParkingSlot createParkingSlot(ParkingSlot parkingSlot) {
        parkingSlot.setStatus(ParkingSlotStatus.AVAILABLE); // Stato iniziale
        return parkingSlotRepository.save(parkingSlot);
    }

    public void deleteParkingSlot(UUID id) {
        if (!parkingSlotRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking slot not found");
        }
        parkingSlotRepository.deleteById(id);
    }

    public ParkingSlot updateParkingSlotStatus(UUID id, ParkingSlotStatus status) {
        ParkingSlot parkingSlot = getParkingSlotById(id);
        parkingSlot.setStatus(status);
        return parkingSlotRepository.save(parkingSlot);
    }
    public boolean existsByLocation(String location) {
        return parkingSlotRepository.existsByLocation(location);
    }
}
