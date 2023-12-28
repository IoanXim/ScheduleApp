package gr.aueb.cf.scheduleapp.model;

import gr.aueb.cf.scheduleapp.model.ids.SpecInstrId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SpecInstrId.class)
@Table(name = "SPECIALTIES_INSTRUCTORS")
public class SpecInstr {

    @Id
    @Column(name = "INSTR_ID")
    private Long instructorId;

    @Id
    @Column(name = "SPEC_ID")
    private Long specialtyId;

}
