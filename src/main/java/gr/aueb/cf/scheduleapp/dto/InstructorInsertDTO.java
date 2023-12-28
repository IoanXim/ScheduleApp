package gr.aueb.cf.scheduleapp.dto;

public class InstructorInsertDTO {

    private String firstname;
    private String lastname;

    private Long ssn;

    public InstructorInsertDTO() {}

    public InstructorInsertDTO(String firstname, String lastname, Long ssn) {
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