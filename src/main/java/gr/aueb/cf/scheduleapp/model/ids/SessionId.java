package gr.aueb.cf.scheduleapp.model.ids;

import java.io.Serializable;

public class SessionId implements Serializable {
    private Long roomId;
    private Long dayId;
    private String startingTime;
    private String endingTime;
}
