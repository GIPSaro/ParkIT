package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.NewUserDTO;
import giorgiaipsaropassione.ParkIT.DTO.UserDTO;
import giorgiaipsaropassione.ParkIT.DTO.UserLoginDTO;
import giorgiaipsaropassione.ParkIT.DTO.UserRespDTO;
import giorgiaipsaropassione.ParkIT.exceptions.BadRequestException;
import giorgiaipsaropassione.ParkIT.services.AuthService;
import giorgiaipsaropassione.ParkIT.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UsersService userService;

    @PostMapping("/login")
    public UserRespDTO login(@RequestBody UserLoginDTO user) {
        return new UserRespDTO(this.authService.checkCredentialAndGenerateToken(user));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserDTO save(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        System.out.println("Ricevuto UserDTO: " + body); // Log per debugging

        if (validationResult.hasErrors()) {
            String msg = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(msg);
        }

        return new NewUserDTO(this.userService.saveUser(body).getId());
    }
    }

