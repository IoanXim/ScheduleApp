package gr.aueb.cf.scheduleapp.model.details;

import gr.aueb.cf.scheduleapp.model.Instructor;
import gr.aueb.cf.scheduleapp.model.Specialty;

public class SpecInstrDetails {
    private Instructor instructor;
    private Specialty specialty;

    public SpecInstrDetails(Instructor instructor, Specialty specialty) {
        this.instructor = instructor;
        this.specialty = specialty;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "SpecInstrDetails{" +
                "instructor=" + instructor +
                ", specialty=" + specialty +
                '}';
    }
}
