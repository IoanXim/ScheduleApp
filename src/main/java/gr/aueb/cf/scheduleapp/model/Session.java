package gr.aueb.cf.scheduleapp.model;

import gr.aueb.cf.scheduleapp.model.ids.SessionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SessionId.class)
@Table(name = "CLASSES")
public class Session {
    @Id
    @Column(name = "ROOM_ID")
    private Long roomId;

    @Id
    @Column(name = "DAY_ID")
    private Long dayId;


    @Column(name = "INSTR_ID")
    private Long instrId;


    @Column(name = "SPEC_ID")
    private Long specId;

    @Id
    @Column(name = "STARTING_TIME")
    private String startingTime;

    @Id
    @Column(name = "ENDING_TIME")
    private String endingTime;
}
