package gr.aueb.cf.scheduleapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CLIENTS")
public class Client {
    @Id
    @Column(name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME", length = 512, nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", length = 512, nullable = false)
    private String lastname;

    @Column(name = "GENDER", length = 512, nullable = false)
    private char gender;

    @Column(name = "BIRTHDAY", length = 512, nullable = false)
    private String birthday;
}
