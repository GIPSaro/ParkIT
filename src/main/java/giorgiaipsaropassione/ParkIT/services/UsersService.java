package giorgiaipsaropassione.ParkIT.services;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giorgiaipsaropassione.ParkIT.DTO.UserDTO;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.exceptions.BadRequestException;
import giorgiaipsaropassione.ParkIT.exceptions.EmailAlreadyExistsException;
import giorgiaipsaropassione.ParkIT.exceptions.NotFoundException;
import giorgiaipsaropassione.ParkIT.repositories.UsersRepository;
import giorgiaipsaropassione.ParkIT.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    public UsersRepository userRepository;
    @Autowired
    public PasswordEncoder bcrypt;

    @Autowired
    public Cloudinary cloudinary;

    @Autowired
    MailgunSender mailgunSender;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // GET PAGES
    public Page<User> getAllUser(int pages, int size, String sortBy, Boolean hasAnnualCard, String startDate) {
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        if (hasAnnualCard != null) {
            return this.userRepository.findByHasAnnualCard(hasAnnualCard, pageable);
        }
        if (startDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            return this.userRepository.findByDateOfRegisterAfter(String.valueOf(start.atStartOfDay()), pageable);
        }
        return this.userRepository.findAll(pageable);
    }


    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " NOT FOUND"));
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
                body.hasAnnualCard(),
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname()
        );


        newUser.setDateOfRegister(LocalDate.now());

        User savedUser = this.userRepository.save(newUser);
        this.mailgunSender.sendRegistrationEmail(newUser);
        return savedUser;
    }


    public User update(UUID userId, UserDTO userUpdateDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con ID: " + userId));

        if (userUpdateDTO.username() != null) {
            existingUser.setUsername(userUpdateDTO.username());
        }

        if (userUpdateDTO.email() != null && !userUpdateDTO.email().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(userUpdateDTO.email())) {
                throw new EmailAlreadyExistsException("L'email è già in uso");
            }
            existingUser.setEmail(userUpdateDTO.email());
        }

        existingUser.setName(userUpdateDTO.name());
        existingUser.setSurname(userUpdateDTO.surname());
        existingUser.setDateOfBirthday(userUpdateDTO.dateOfBirthday());
        existingUser.setLicensePlate(userUpdateDTO.licensePlate());


        existingUser.setDateOfRegister(userUpdateDTO.dateOfRegister());

        return userRepository.save(existingUser);
    }


    //IMG UPLOAD
    public String imgUpload(MultipartFile file, UUID id) throws IOException, MaxUploadSizeExceededException {
        User userFound = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        userFound.setAvatar(url);
        this.userRepository.save(userFound);
        return url;
    }

    // GET PAGES
    public Page<User> getAllUser(int pages, int size, String sortBy) {
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }

    // GET user con Optional
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User findFromEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }
}