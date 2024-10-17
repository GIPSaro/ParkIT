package giorgiaipsaropassione.ParkIT.services;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import giorgiaipsaropassione.ParkIT.DTO.UserDTO;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.exceptions.BadRequestException;
import giorgiaipsaropassione.ParkIT.exceptions.EmailAlreadyExistsException;
import giorgiaipsaropassione.ParkIT.exceptions.NotFoundException;
import giorgiaipsaropassione.ParkIT.repositories.UsersRepository;
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
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    public UsersRepository userRepository;
    @Autowired
    public PasswordEncoder bcrypt;

    @Autowired
    public Cloudinary cloudinary;

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
                body.hasAnnualCard(),
                body.avatar()

        );

        return userRepository.save(newUser);
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
}