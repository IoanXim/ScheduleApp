package gr.aueb.cf.scheduleapp.dto;

public class SpecialtyReadOnlyDTO extends BaseDTO{

    private String name;

    public SpecialtyReadOnlyDTO() {
    }

    public SpecialtyReadOnlyDTO(Long id, String name) {
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
