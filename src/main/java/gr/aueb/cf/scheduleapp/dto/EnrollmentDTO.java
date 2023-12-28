package gr.aueb.cf.scheduleapp.dto;

public class EnrollmentDTO {

    private Long clientId;
    private Long roomId;
    private Long dayId;

    private String startingTime;
    private String endingTime;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(Long clientId, Long roomId, Long dayId, String startingTime, String endingTime) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.dayId = dayId;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getDayId() {
        return dayId;
    }

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }
}
