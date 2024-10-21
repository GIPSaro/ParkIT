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
class UsersController {

    @Autowired
    private UsersService usersService;

    // GET per utenti
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Page<User> users = usersService.getAllUser(page, size, sortBy);
        return ResponseEntity.ok(users);
    }

    // GET utente per ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = usersService.findById(id);
        return ResponseEntity.ok(user);
    }

    // POST nuovo utente
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = usersService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // PUT aggiorna l'utente
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        User existingUser = usersService.findById(id);

        // Aggiorna i campi del utente
        existingUser.setUsername(userDTO.username());
        existingUser.setEmail(userDTO.email());
        existingUser.setPassword(usersService.bcrypt.encode(userDTO.password()));
        existingUser.setName(userDTO.name());
        existingUser.setSurname(userDTO.surname());
        existingUser.setAvatar("https://ui-avatars.com/api/?name=" + userDTO.name() + "+" + userDTO.surname());

        // Salva l'utente aggiornato
        User updatedUser = usersService.saveUser(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE elimina utente - accessibile solo agli ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        User userToDelete = usersService.findById(id);
        usersService.userRepository.delete(userToDelete);
        return ResponseEntity.noContent().build();
    }

    // Endpoint /me
//    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser() {

        // Ottenere l'utente autenticato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Recuperare l'utente dal database tramite UsersService
        User authenticatedUser = usersService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente autenticato non trovato"));

        // Restituire le informazioni dell'utente autenticato
        return ResponseEntity.ok(authenticatedUser);
    }

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



