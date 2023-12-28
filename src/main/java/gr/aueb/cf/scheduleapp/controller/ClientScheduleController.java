package gr.aueb.cf.scheduleapp.controller;


import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.model.*;
import gr.aueb.cf.scheduleapp.model.details.SessionDetails;
import gr.aueb.cf.scheduleapp.service.*;
import gr.aueb.cf.scheduleapp.validator.EnrollmentValidator;
import gr.aueb.cf.scheduleapp.validator.UserInsertValidator;
import gr.aueb.cf.scheduleapp.validator.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientScheduleController {

    private ISessionService sessionService;
    private IEnrollmentService enrollmentService;
    private IRoomService roomService;
    private IClientService clientService;
    private IInstructorService instructorService;
    private ISpecialtyService specialtyService;
    private ISpecInstrService specInstrService;
    private IUserService userService;
    private IUserClientService userClientService;
    private UserInsertValidator userInsertValidator;
    private UserUpdateValidator userUpdateValidator;
    private EnrollmentValidator enrollmentValidator;


    @Autowired
    public ClientScheduleController(ISessionService sessionService, IEnrollmentService enrollmentService, IRoomService roomService, IClientService clientService, IInstructorService instructorService, ISpecialtyService specialtyService, ISpecInstrService specInstrService, IUserService userService, IUserClientService userClientService, UserInsertValidator userInsertValidator, UserUpdateValidator userUpdateValidator, EnrollmentValidator enrollmentValidator) {
        this.sessionService = sessionService;
        this.enrollmentService = enrollmentService;
        this.roomService = roomService;
        this.clientService = clientService;
        this.instructorService = instructorService;
        this.specialtyService = specialtyService;
        this.specInstrService = specInstrService;
        this.userService = userService;
        this.userClientService = userClientService;
        this.userInsertValidator = userInsertValidator;
        this.userUpdateValidator = userUpdateValidator;
        this.enrollmentValidator = enrollmentValidator;
    }




    @GetMapping("/client/myschedule")
    public String showMySchedule(Model model, HttpSession httpSession) {
        if ( httpSession.getAttribute("user") == null) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        User user = (User) httpSession.getAttribute("user");
        try {
            Long clientId = userClientService.getUserClientByUserId(user.getId()).getClientId();
            if (clientId == null) {
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

            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByClientId(clientId);
            List<SessionDetails> sessionDetailsList = new ArrayList<>();

            for (Enrollment enrollment : enrollments) {
                Session session = sessionService.getSessionByRoomIdDayIdAndTimes(enrollment.getRoomId(), enrollment.getDayId(), enrollment.getStartingTime(), enrollment.getEndingTime());
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
            model.addAttribute("sessionDetailsList", sessionDetailsList);
            model.addAttribute("timeSlots", timeSlots);
            model.addAttribute("daysOfWeek", daysOfWeek);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "mySchedulePage";
    }

    @PostMapping("/client/deleteEnrollment")
    public ResponseEntity<String> deleteEnrollment(
            @RequestParam("roomId") Long roomId,
            @RequestParam("dayId") Long dayId,
            @RequestParam("startingTime") String startingTime,
            @RequestParam("endingTime") String endingTime,
            HttpSession httpSession
    ) {
        if ( httpSession.getAttribute("user") == null) {
            httpSession.invalidate();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid http session");
        }
        User user = (User) httpSession.getAttribute("user");
        try {
            Long clientId = userClientService.getUserClientByUserId(user.getId()).getClientId();
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO(clientId, roomId, dayId, startingTime, endingTime);
            enrollmentService.deleteEnrollment(enrollmentDTO);
            return ResponseEntity.ok("Enrollment deleted successfully");
        } catch (Exception e) {
            String errorMessage = "Error: " + e.getMessage();
            errorMessage = errorMessage.split("\\[")[1];
            errorMessage = errorMessage.split("]")[0];
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/client/schedule")
    public String showSchedule(Model model, HttpSession httpSession) {
        if ( httpSession.getAttribute("user") == null) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        User user = (User) httpSession.getAttribute("user");
        try {
            Long clientId = userClientService.getUserClientByUserId(user.getId()).getClientId();
            if (clientId == null) {
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

            List<Room> rooms = roomService.getAllRooms();
            List<Session> sessionsList = sessionService.getAllSessions();
            List<SessionDetails> sessionDetailsList = new ArrayList<>();


            for (int i = 0; i < sessionsList.size(); i++) {
                Instructor instructor = instructorService.getInstructorById(sessionsList.get(i).getInstrId());
                Specialty specialty = specialtyService.getSpecialtyById(sessionsList.get(i).getSpecId());
                Long dayId = sessionsList.get(i).getDayId();
                Room room = roomService.getRoomById(sessionsList.get(i).getRoomId());

                LocalTime localStartingTime = LocalTime.parse(sessionsList.get(i).getStartingTime());
                LocalTime localEndingTime = LocalTime.parse(sessionsList.get(i).getEndingTime());
                LocalTime localDuration = localEndingTime.minusHours(localStartingTime.getHour());
                int duration = localDuration.getHour();

                for (int j = 0; j < duration; j++) {
                    LocalTime localSlot = LocalTime.parse(sessionsList.get(i).getStartingTime()).plusHours(j);
                    String slot = localSlot.toString();
                    slot = slot.split(":")[0];
                    slot = slot + ":00";
                    SessionDetails sessionDetails = new SessionDetails(sessionsList.get(i), instructor, specialty, dayId, room, slot, j + 1, duration);
                    sessionDetailsList.add(sessionDetails);
                }

            }
            model.addAttribute("clientId", clientId);
            model.addAttribute("rooms", rooms);
            model.addAttribute("sessionDetailsList", sessionDetailsList);
            model.addAttribute("sessionsList", sessionsList);
            model.addAttribute("timeSlots", timeSlots);
            model.addAttribute("daysOfWeek", daysOfWeek);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "scheduleEnrollPage";
    }

    @PostMapping("/client/enrollInSession")
    public ResponseEntity<String> addEnrollment(
            @RequestParam("roomId") Long roomId,
            @RequestParam("dayId") Long dayId,
            @RequestParam("startingTime") String startingTime,
            @RequestParam("endingTime") String endingTime,
            HttpSession httpSession
    ) {
        if ( httpSession.getAttribute("user") == null) {
            httpSession.invalidate();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid http session");
        }
        User user = (User) httpSession.getAttribute("user");
        try {
            Long clientId = userClientService.getUserClientByUserId(user.getId()).getClientId();
            EnrollmentDTO enrollmentDTO = new EnrollmentDTO(clientId,roomId, dayId, startingTime, endingTime);
            enrollmentService.insertEnrollment(enrollmentDTO);
            return ResponseEntity.ok("Enrollment added successfully");
        } catch (Exception e) {
            String errorMessage = "Error: " + e.getMessage();
            errorMessage = errorMessage.split("\\[")[1];
            errorMessage = errorMessage.split("]")[0];
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
