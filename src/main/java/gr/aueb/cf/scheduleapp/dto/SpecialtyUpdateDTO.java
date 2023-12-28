package gr.aueb.cf.scheduleapp.dto;

public class SpecialtyUpdateDTO extends BaseDTO{

    private String name;

    public SpecialtyUpdateDTO() {
    }

    public SpecialtyUpdateDTO(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
