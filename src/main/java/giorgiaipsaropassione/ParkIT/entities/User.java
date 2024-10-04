package giorgiaipsaropassione.ParkIT.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    private String email;
    private String password;
    private String name;
    private String surname;
    private Date dateOfBirthday;
    private String licensePlate;

    public User(String username, String email, String password, String name, String surname, Date dateOfBirthday, String licensePlate){
        this.username=username;
        this.email=email;
        this.password=password;
        this.name=name;
        this.surname=surname;
        this.dateOfBirthday=dateOfBirthday;
        this.licensePlate=licensePlate;
    }

}
