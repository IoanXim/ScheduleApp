package gr.aueb.cf.scheduleapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INSTRUCTORS")
public class Instructor {
    @Id
    @Column(name = "INSTR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRSTNAME", length = 512, nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", length = 512, nullable = false)
    private String lastname;

    @Column(name = "SSN", length = 512, nullable = false)
    private Long ssn;

}
