package gr.aueb.cf.scheduleapp.model;

import gr.aueb.cf.scheduleapp.model.ids.EnrollmentId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EnrollmentId.class)
@Table(name = "ENROLLMENTS")
public class Enrollment {

    @Id
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Id
    @Column(name = "ROOM_ID")
    private Long roomId;

    @Id
    @Column(name = "DAY_ID")
    private Long dayId;

    @Id
    @Column(name = "STARTING_TIME")
    private String startingTime;

    @Id
    @Column(name = "ENDING_TIME")
    private String endingTime;
}
