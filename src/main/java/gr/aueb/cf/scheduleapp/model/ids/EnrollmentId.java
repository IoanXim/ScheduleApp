package gr.aueb.cf.scheduleapp.model.ids;

import java.io.Serializable;

public class EnrollmentId implements Serializable {
    private Long clientId;
    private Long roomId;
    private Long dayId;
    private String startingTime;
    private String endingTime;
}
