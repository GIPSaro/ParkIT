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
//aggiungere avatar che servirà per la post dell'user
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
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate dateOfBirthday;
    private String licensePlate;

    //se l'utente decide di comprare la tessera
    private boolean hasAnnualCard;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String avatar;
    @OneToOne(mappedBy = "user")
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


    }



    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
}
