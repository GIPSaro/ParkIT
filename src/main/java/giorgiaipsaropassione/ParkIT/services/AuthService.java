package giorgiaipsaropassione.ParkIT.services;

import giorgiaipsaropassione.ParkIT.DTO.UserLoginDTO;
import giorgiaipsaropassione.ParkIT.DTO.UserRespDTO;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.exceptions.UnauthorizedException;
import giorgiaipsaropassione.ParkIT.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UsersService userService;

    @Autowired
    JWTTools jwtTools;

    @Autowired
    PasswordEncoder bcrypt;


    public UserRespDTO checkCredentialAndGenerateToken(UserLoginDTO body) {
        User found = this.userService.findFromEmail(body.email());
        if (found != null && bcrypt.matches(body.password(), found.getPassword())) {
            String token = jwtTools.createToken(found);
            String role = String.valueOf(found.getRole());
            return new UserRespDTO(token, role);
        } else {
            throw new UnauthorizedException("CREDENTIALS ARE NOT VALID");
        }
    }
}



