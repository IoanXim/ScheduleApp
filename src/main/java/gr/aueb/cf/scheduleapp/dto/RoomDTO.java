package gr.aueb.cf.scheduleapp.dto;

public class RoomDTO {
    private Long id;

    public RoomDTO() {
    }

    public RoomDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
