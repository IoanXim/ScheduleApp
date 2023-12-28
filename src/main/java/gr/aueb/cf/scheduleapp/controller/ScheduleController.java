package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.dto.SessionDTO;
import gr.aueb.cf.scheduleapp.model.*;
import gr.aueb.cf.scheduleapp.model.details.SessionDetails;
import gr.aueb.cf.scheduleapp.model.details.SpecInstrDetails;
import gr.aueb.cf.scheduleapp.service.*;
import gr.aueb.cf.scheduleapp.validator.SessionValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {


    private ISessionService sessionService;
    private IRoomService roomService;
    private IInstructorService instructorService;
    private ISpecialtyService specialtyService;
    private ISpecInstrService specInstrService;
    private IEnrollmentService enrollmentService;
    private SessionValidator sessionValidator;

    @Autowired
    public ScheduleController(ISessionService sessionService, IRoomService roomService, IInstructorService instructorService, ISpecialtyService specialtyService, ISpecInstrService specInstrService, IEnrollmentService enrollmentService, SessionValidator sessionValidator) {
        this.sessionService = sessionService;
        this.roomService = roomService;
        this.instructorService = instructorService;
        this.specialtyService = specialtyService;
        this.specInstrService = specInstrService;
        this.enrollmentService = enrollmentService;
        this.sessionValidator = sessionValidator;
    }

    @GetMapping("/schedule")
    public String showSchedule(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        List<String> timeSlots = new ArrayList<>();
        List<Long> daysOfWeek = new ArrayList<>();
        timeSlots.add("09:00");
        for (int i = 10; i < 22; i++) {
            timeSlots.add(i + ":00");
        }
        for (int i = 1; i < 8; i++) {
            daysOfWeek.add((long) i);
        }
        try {
            List<Room> rooms = roomService.getAllRooms();
            List<Session> sessionsList = sessionService.getAllSessions();
            List<SessionDetails> sessionDetailsList = new ArrayList<>();


            for (Session session : sessionsList) {
                Instructor instructor = instructorService.getInstructorById(session.getInstrId());
                Specialty specialty = specialtyService.getSpecialtyById(session.getSpecId());
                Long dayId = session.getDayId();
                Room room = roomService.getRoomById(session.getRoomId());

                LocalTime localStartingTime = LocalTime.parse(session.getStartingTime());
                LocalTime localEndingTime = LocalTime.parse(session.getEndingTime());
                LocalTime localDuration = localEndingTime.minusHours(localStartingTime.getHour());
                int duration = localDuration.getHour();

                for (int j = 0; j < duration; j++) {
                    LocalTime localSlot = LocalTime.parse(session.getStartingTime()).plusHours(j);
                    String slot = localSlot.toString();
                    slot = slot.split(":")[0];
                    slot = slot + ":00";
                    SessionDetails sessionDetails = new SessionDetails(session, instructor, specialty, dayId, room, slot, j + 1, duration);
                    sessionDetailsList.add(sessionDetails);
                }

            }
            model.addAttribute("rooms", rooms);
            model.addAttribute("sessionDetailsList", sessionDetailsList);
            model.addAttribute("sessionsList", sessionsList);
            model.addAttribute("timeSlots", timeSlots);
            model.addAttribute("daysOfWeek", daysOfWeek);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return "schedulePage";
    }

    @GetMapping("schedule/insert")
    public String showSessionInsertForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try{
            List<String> timeSlots = new ArrayList<>();
            timeSlots.add("09:00");
            for (int i = 10; i < 22; i++) {
                timeSlots.add(i + ":00");
            }
            List<Integer> availableDuration = new ArrayList<>();
            for (int i = 1; i < 5; i++){
                availableDuration.add(i);
            }
            List<Room> rooms = roomService.getAllRooms();

            List<SpecInstr> specInstrs = specInstrService.getAllSpecInstrs();
            List<SpecInstrDetails> specInstrDetailsList = new ArrayList<>();
            for (SpecInstr specInstr : specInstrs) {
                Instructor instructor = instructorService.getInstructorById(specInstr.getInstructorId());
                Specialty specialty = specialtyService.getSpecialtyById(specInstr.getSpecialtyId());
                SpecInstrDetails specInstrDetails = new SpecInstrDetails(instructor, specialty);
                specInstrDetailsList.add(specInstrDetails);
            }

            model.addAttribute("specInstrDetailsList", specInstrDetailsList);
            model.addAttribute("rooms", rooms);
            model.addAttribute("timeSlots", timeSlots);
            model.addAttribute("durations", availableDuration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "sessionInsertForm";
    }


    @PostMapping("schedule/insert")
    public String addSession(@RequestParam("instructorSpecialty") String instructorSpecialtyId,
                             @RequestParam("room") Long roomId,
                             @RequestParam("dayOfWeek") Long dayId,
                             @RequestParam("timeSlot") String timeSlot,
                             @RequestParam("duration") Integer duration,
                             HttpSession httpSession
    ) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            String[] ids = instructorSpecialtyId.split("-");
            Long instrId = Long.parseLong(ids[0]);
            Long specId = Long.parseLong(ids[1]);
            String startingTime = timeSlot;
            LocalTime localTimeSlot = LocalTime.parse(timeSlot);
            LocalTime localEndingTime = localTimeSlot.plusHours(duration);
            String endingTime = localEndingTime.toString();
            SessionDTO sessionDTO = new SessionDTO(roomId, dayId, instrId, specId, startingTime, endingTime);
            sessionService.insertSession(sessionDTO);
        } catch (Exception e) {
            return "redirect:/schedule/insert";
        }

        return "redirect:/schedule";
    }

    @PostMapping("/deleteSession")
    public ResponseEntity<String> deleteSession(
            @RequestParam("roomId") Long roomId,
            @RequestParam("dayId") Long dayId,
            @RequestParam("instrId") Long instrId,
            @RequestParam("specId") Long specId,
            @RequestParam("startingTime") String startingTime,
            @RequestParam("endingTime") String endingTime,
            HttpSession httpSession

    ) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
        }
        try {
            SessionDTO sessionDTO = new SessionDTO(roomId, dayId, instrId, specId, startingTime, endingTime);
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByRoomIdDayIdAndTimes(roomId, dayId, startingTime, endingTime);
            for (Enrollment enrollment : enrollments) {
                EnrollmentDTO enrollmentDTO = new EnrollmentDTO(enrollment.getClientId(), enrollment.getRoomId(), enrollment.getDayId(), enrollment.getStartingTime(), enrollment.getEndingTime());
                enrollmentService.deleteEnrollment(enrollmentDTO);
            }
            sessionService.deleteSession(sessionDTO);
            return ResponseEntity.ok("Session deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}


