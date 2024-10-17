package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.UserDTO;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;


    // ** stampa tutti gli utenti
    @GetMapping
    // può farlo solo admin
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy){
        return this.usersService.getAllUser(page, size, sortBy );
    }

    // GET utente per ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
      return this.usersService.findById(id);
    }

    // POST nuovo utente
    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO) {
        return this.usersService.saveUser(userDTO);
    }

    // PUT aggiorna l'utente
    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        User existingUser = usersService.findById(id);

        // Aggiorna i campi del utente
        existingUser.setUsername(userDTO.username());
        existingUser.setEmail(userDTO.email());
        existingUser.setPassword(usersService.bcrypt.encode(userDTO.password()));
        existingUser.setName(userDTO.name());
        existingUser.setSurname(userDTO.surname());
        // Salva l'utente aggiornato
        return this.usersService.saveUser(userDTO);
    }

    // DELETE - accessibile solo agli ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        User userToDelete = usersService.findById(id);
        usersService.userRepository.delete(userToDelete);
    }

    // Endpoint /me

    //GET ME
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User userAuthenticated) {
        return userAuthenticated;
    }

    // PUT ME
    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User userAuthenticated, @RequestBody UserDTO payload) {
        return this.usersService.update(userAuthenticated.getId(), payload);
    }


    //POST ME IMG
    @PostMapping("/me/avatar")
    public ResponseEntity<Map<String, String>> imgUploadME(@RequestParam("avatar") MultipartFile img,
                                                           @AuthenticationPrincipal User userAuthenticated) throws IOException, MaxUploadSizeExceededException {
        String avatarUrl = this.usersService.imgUpload(img, userAuthenticated.getId());

        Map<String, String> response = new HashMap<>();
        response.put("avatar", avatarUrl);
        return ResponseEntity.ok(response);
    }


}