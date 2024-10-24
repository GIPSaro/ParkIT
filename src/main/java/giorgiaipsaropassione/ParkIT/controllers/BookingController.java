package giorgiaipsaropassione.ParkIT.controllers;


import giorgiaipsaropassione.ParkIT.DTO.BookingDTO;
import giorgiaipsaropassione.ParkIT.entities.Booking;
import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody BookingDTO bookingDTO) {
        User user = new User(); // Recuperare dal database tramite userId in BookingDTO
        user.setId(bookingDTO.getUserId());

        ParkingSlot parkingSlot = new ParkingSlot(); // Recuperare dal database tramite parkingSlotId
        parkingSlot.setId(bookingDTO.getParkingSlotId());

        return bookingService.createBooking(
                user,
                parkingSlot,
                bookingDTO.getStartTime(),
                bookingDTO.getEndTime()
        );
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
