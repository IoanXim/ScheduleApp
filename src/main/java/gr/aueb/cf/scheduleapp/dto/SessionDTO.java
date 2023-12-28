package gr.aueb.cf.scheduleapp.dto;

public class SessionDTO {
    private Long roomId;
    private Long dayId;
    private Long instrId;
    private Long specId;
    private String startingTime;
    private String endingTime;

    public SessionDTO() {
    }

    public SessionDTO(Long roomId, Long dayId, Long instrId, Long specId, String startingTime, String endingTime) {
        this.roomId = roomId;
        this.dayId = dayId;
        this.instrId = instrId;
        this.specId = specId;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
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

    public Long getInstrId() {
        return instrId;
    }

    public void setInstrId(Long instrId) {
        this.instrId = instrId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
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
