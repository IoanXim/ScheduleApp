package gr.aueb.cf.scheduleapp.dto;

public class InstructorUpdateDTO extends BaseDTO {
    private String firstname;
    private String lastname;

    private Long ssn;

    public InstructorUpdateDTO() {}

    public InstructorUpdateDTO(Long id, String firstname, String lastname, Long ssn) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.ssn = ssn;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getSsn() {
        return ssn;
    }

    public void setSsn(Long ssn) {
        this.ssn = ssn;
    }
}
