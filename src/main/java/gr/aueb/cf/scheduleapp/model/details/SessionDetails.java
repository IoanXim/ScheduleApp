package gr.aueb.cf.scheduleapp.model.details;

import gr.aueb.cf.scheduleapp.model.Instructor;
import gr.aueb.cf.scheduleapp.model.Room;
import gr.aueb.cf.scheduleapp.model.Session;
import gr.aueb.cf.scheduleapp.model.Specialty;

public class SessionDetails {
    private Session session;
    private Instructor instructor;
    private Specialty specialty;
    private Long dayId;
    private Room room;
    private String slot;
    private int hour;

    private int duration;

    public SessionDetails(Session session, Instructor instructor, Specialty specialty, Long dayId, Room room, String slot, int hour, int duration) {
        this.session = session;
        this.instructor = instructor;
        this.specialty = specialty;
        this.dayId = dayId;
        this.room = room;
        this.slot = slot;
        this.hour = hour;
        this.duration = duration;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public Long getDayId() {
        return dayId;
    }

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SessionDetails{" +
                "session=" + session +
                ", instructor=" + instructor +
                ", specialty=" + specialty +
                ", dayId=" + dayId +
                ", room=" + room +
                ", slot='" + slot + '\'' +
                ", hour=" + hour +
                ", duration=" + duration +
                '}';
    }
}
