package gr.aueb.cf.scheduleapp.dto;

public class ClientReadOnlyDTO extends BaseDTO {
    private String firstname;
    private String lastname;
    private char gender;
    private String birthday;

    public ClientReadOnlyDTO() {
    }

    public ClientReadOnlyDTO(Long id, String firstname, String lastname, char gender, String birthday) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthday = birthday;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
