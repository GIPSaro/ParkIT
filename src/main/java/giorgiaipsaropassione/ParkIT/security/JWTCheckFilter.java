package giorgiaipsaropassione.ParkIT.security;


import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.exceptions.UnauthorizedException;
import giorgiaipsaropassione.ParkIT.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    @Autowired
    JWTTools jwtTools;

    @Autowired
    UsersService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            System.out.println("Authorization Header Missing or Incorrect");
            throw new UnauthorizedException("INSERT DATA CORRECTLY");
        }

        String accessToken = authHeader.substring(7);
        try {
            jwtTools.verifyToke(accessToken);
        } catch (Exception e) {
            System.out.println("Token verification failed: " + e.getMessage());
            throw new UnauthorizedException("Invalid Token");
        }

        String id = this.jwtTools.extractIdFromToken(accessToken);
        User userFromDB = this.userService.findById(UUID.fromString(id));
        if (userFromDB == null) {
            System.out.println("User not found for ID: " + id);
            throw new UnauthorizedException("User not found");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(userFromDB, null, userFromDB.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }


    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
