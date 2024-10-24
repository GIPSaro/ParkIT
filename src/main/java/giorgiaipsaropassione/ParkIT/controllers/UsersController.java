package giorgiaipsaropassione.ParkIT.controllers;

import giorgiaipsaropassione.ParkIT.DTO.AnnualCardDTO;
import giorgiaipsaropassione.ParkIT.DTO.UserDTO;
import giorgiaipsaropassione.ParkIT.entities.AnnualCard;
import giorgiaipsaropassione.ParkIT.entities.User;
import giorgiaipsaropassione.ParkIT.services.AnnualCardService;
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

    @Autowired
    private AnnualCardService annualCardService;
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

    //------------ADMIN--------------//

    // PUT
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{id}")
//    public User updateProfileUser(@AuthenticationPrincipal User userAuthenticated, @RequestBody UserDTO payload) {
//        // Log dell'utente autenticato e del payload
//        System.out.println("User Authenticated: " + userAuthenticated);
//        System.out.println("Payload: " + payload);
//        return this.usersService.update(userAuthenticated.getId(), payload);
//    }
//
//    // DELETE
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
//        User userToDelete = usersService.findById(id);
//        usersService.userRepository.delete(userToDelete);
//        return ResponseEntity.noContent().build();
//    }

   //-----------------------ME---------------//

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

    // DELETE ME
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUserMe(@AuthenticationPrincipal User userAuthenticated) {

        usersService.userRepository.delete(userAuthenticated);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        User userToDelete = usersService.findById(id);
        usersService.userRepository.delete(userToDelete);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public User updateProfileUser(@PathVariable UUID id, @RequestBody UserDTO payload) {
        // Log dell'utente autenticato e del payload
        System.out.println("User Authenticated: " + id);
        System.out.println("Payload: " + payload);
        
        return this.usersService.update(id, payload);
    }


    //ANNUAL CARD

    @GetMapping("/{userId}/annualCard")
    public AnnualCardDTO getAnnualCard(@PathVariable UUID userId) {
        AnnualCard annualCard = annualCardService.getAnnualCardForUser(userId);
        return new AnnualCardDTO(annualCard.getStartDate(), annualCard.getEndDate(), annualCard.getPrice());
    }




}



