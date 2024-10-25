package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.BookingDTO;
import giorgiaipsaropassione.ParkIT.entities.Booking;
import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.enums.ParkingSlotStatus;
import giorgiaipsaropassione.ParkIT.services.BookingService;
import giorgiaipsaropassione.ParkIT.services.ParkingSlotService;
import giorgiaipsaropassione.ParkIT.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ParkingSlotService parkingSlotService;

    @Autowired
    private UsersService userService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        UUID parkingSlotId = bookingDTO.getParkingSlotId();
        UUID userId = bookingDTO.getUserId();

        // Recupera l'oggetto ParkingSlot
        ParkingSlot parkingSlot = parkingSlotService.getParkingSlotById(parkingSlotId);

        // Verifica che lo slot sia disponibile
        if (parkingSlot.getStatus() != ParkingSlotStatus.AVAILABLE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        // Riserva lo slot di parcheggio
        parkingSlotService.updateParkingSlotStatus(parkingSlotId, ParkingSlotStatus.RESERVED);

        // Recupera l'oggetto User usando il suo ID
        User user = userService.findById(userId);

        // Crea la prenotazione
        Booking booking = bookingService.createBooking(
                user,
                parkingSlot,
                bookingDTO.getStartTime(),
                bookingDTO.getEndTime()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/{bookingId}/free")
    public ResponseEntity<String> freeBooking(@PathVariable UUID bookingId) {
        bookingService.freeBooking(bookingId);
        return ResponseEntity.ok("The booking has been freed and the parking slot is now available.");
    }
}
