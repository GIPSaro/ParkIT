package giorgiaipsaropassione.ParkIT.services;

import giorgiaipsaropassione.ParkIT.entities.Booking;
import giorgiaipsaropassione.ParkIT.entities.ParkingSlot;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.enums.BookingStatus;
import giorgiaipsaropassione.ParkIT.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(User user, ParkingSlot parkingSlot, LocalDateTime startTime, LocalDateTime endTime) {
        // Verifica se c'è già una prenotazione associata al parcheggio
        Optional<Booking> existingBooking = bookingRepository.findByParkingSlotAndEndTimeIsNull(parkingSlot);

        if (existingBooking.isPresent() && existingBooking.get().getStatus() == BookingStatus.OCCUPIED) {
            throw new IllegalStateException("The parking slot is currently occupied. Please choose another one.");
        }

        // Se non è occupato, crea la prenotazione
        Booking booking = new Booking(user, parkingSlot, startTime, endTime, BookingStatus.OCCUPIED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Metodo per liberare una prenotazione
    public void freeBooking(UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setStatus(BookingStatus.FREE);
            booking.setEndTime(LocalDateTime.now());
            bookingRepository.save(booking);
        }
    }
}
