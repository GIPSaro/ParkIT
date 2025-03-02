package giorgiaipsaropassione.ParkIT.security;

import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {

        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis()))
                .claim("role", user.getRole())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();

    }

    public void verifyToke(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parse(token);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println( e.getMessage());
            throw new UnauthorizedException("Token expired, please login again.");
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println( e.getMessage());
            throw new UnauthorizedException("Invalid token signature, please login again.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UnauthorizedException("PROBLEMS WITH TOKEN, TRY TO LOGIN");
        }
    }

    public String extractIdFromToken(String accessToken) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(accessToken).getPayload().getSubject();
    }

}
