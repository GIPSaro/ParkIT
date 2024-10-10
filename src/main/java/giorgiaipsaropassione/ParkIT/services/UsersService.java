package giorgiaipsaropassione.ParkIT.services;



import giorgiaipsaropassione.ParkIT.DTO.UserDTO;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.exceptions.BadRequestException;
import giorgiaipsaropassione.ParkIT.exceptions.NotFoundException;
import giorgiaipsaropassione.ParkIT.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    public UsersRepository userRepository;
    @Autowired
    public PasswordEncoder bcrypt;


    // GET PAGES
    public Page<User> getAllUser(int pages, int size, String sortBy) {
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }

    public User findFromEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }


    // GET by id
    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST per SAVE USER
    public User saveUser(UserDTO body) {
        if (this.userRepository.existsByEmail(body.email())) {
            throw new BadRequestException("User with this email already exists");
        }
        User newUser = new User(
                body.username(),
                body.email(),
                this.bcrypt.encode(body.password()),
                body.name(),
                body.surname(),
                body.dateOfBirthday(),
                body.licensePlate(),
                body.hasAnnualCard()
        );

        return userRepository.save(new User());
    }

    public User update(UUID id, UserDTO payload) {
        User found = this.findById(id);
        found.setUsername(payload.username());
        found.setEmail(payload.email());
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setDateOfBirthday(payload.dateOfBirthday());
        found.setLicensePlate(payload.licensePlate());
        return this.userRepository.save(found);
    }
}