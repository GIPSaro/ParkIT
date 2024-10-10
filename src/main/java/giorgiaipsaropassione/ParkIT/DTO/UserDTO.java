package giorgiaipsaropassione.ParkIT.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserDTO(
        @NotEmpty(message = "Username is required")
        @Size (min = 2, max = 20, message = "Username has to be from 2 to 20 characters")
        String username,

        @NotEmpty(message = "Email is required")
        String email,

        @NotEmpty(message = "Password is required")
        String password,

        @NotEmpty(message = "Name is required")
        @Size(min = 2, max = 30, message = "Name has to be from 2 to 30 characters")
        String name,

        @NotEmpty(message = "Surname is required")
        String surname,

        @NotEmpty(message = "Date of Birthday is required")
        LocalDate dateOfBirthday,

        @NotEmpty(message = "License Plate is required")
        String licensePlate,

        boolean hasAnnualCard
) {
}


