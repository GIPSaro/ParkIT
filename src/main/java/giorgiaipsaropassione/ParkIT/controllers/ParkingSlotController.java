package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.ParkingSlotDTO;
import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import giorgiaipsaropassione.ParkIT.enums.ParkingSlotStatus;
import giorgiaipsaropassione.ParkIT.repositories.ParkingSlotRepository;
import giorgiaipsaropassione.ParkIT.services.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/parkingslots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;
    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @GetMapping
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotService.getAllParkingSlots();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getParkingSlotById(@PathVariable UUID id) {
        ParkingSlot parkingSlot = parkingSlotService.getParkingSlotById(id);
        return ResponseEntity.ok(parkingSlot);
    }

    @PostMapping
    public ResponseEntity<ParkingSlot> createParkingSlot(@RequestBody ParkingSlotDTO parkingSlotDTO) {
        ParkingSlot parkingSlot = new ParkingSlot(parkingSlotDTO.getLocation(), ParkingSlotStatus.AVAILABLE);
        ParkingSlot createdParkingSlot = parkingSlotService.createParkingSlot(parkingSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParkingSlot);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParkingSlot(@PathVariable UUID id) {
        parkingSlotService.deleteParkingSlot(id);
        return ResponseEntity.ok("Parking slot deleted successfully.");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ParkingSlot> updateParkingSlotStatus(@PathVariable UUID id, @RequestBody ParkingSlotStatus status) {
        ParkingSlot updatedParkingSlot = parkingSlotService.updateParkingSlotStatus(id, status);
        return ResponseEntity.ok(updatedParkingSlot);
    }
}
