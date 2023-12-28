package gr.aueb.cf.scheduleapp.dto;

public class SpecialtyInsertDTO {

    private String name;

    public SpecialtyInsertDTO() {
    }

    public SpecialtyInsertDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
