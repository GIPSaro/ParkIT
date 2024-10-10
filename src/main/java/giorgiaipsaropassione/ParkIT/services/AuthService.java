package giorgiaipsaropassione.ParkIT.services;

import giorgiaipsaropassione.ParkIT.DTO.UserLoginDTO;
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


    public String checkCredentialAndGenerateToken(UserLoginDTO body) {
        User found = this.userService.findFromEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("CREDENTIAL ARE NOT VALID");
        }
    }

}
