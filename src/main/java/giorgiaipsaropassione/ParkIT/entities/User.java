package giorgiaipsaropassione.ParkIT.entities;

import giorgiaipsaropassione.ParkIT.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate dateOfBirthday;
    private String licensePlate;


    private boolean hasAnnualCard;

    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate dateOfRegister;

    private String avatar;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AnnualCard annualCard;


    public User(String username, String email, String password, String name, String surname, LocalDate dateOfBirthday, String licensePlate, boolean hasAnnualCard, String avatar) {
        this.username=username;
        this.email=email;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.dateOfBirthday=dateOfBirthday;
        this.licensePlate=licensePlate;
        this.hasAnnualCard = hasAnnualCard;
        this.avatar = avatar;
        this.role = Role.USER;
        this.dateOfRegister = LocalDate.now();


    }



    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
