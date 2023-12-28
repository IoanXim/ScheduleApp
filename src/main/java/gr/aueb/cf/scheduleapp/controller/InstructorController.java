package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.*;
import gr.aueb.cf.scheduleapp.model.Enrollment;
import gr.aueb.cf.scheduleapp.model.Instructor;
import gr.aueb.cf.scheduleapp.model.Session;
import gr.aueb.cf.scheduleapp.model.SpecInstr;
import gr.aueb.cf.scheduleapp.service.IEnrollmentService;
import gr.aueb.cf.scheduleapp.service.IInstructorService;
import gr.aueb.cf.scheduleapp.service.ISessionService;
import gr.aueb.cf.scheduleapp.service.ISpecInstrService;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.scheduleapp.validator.InstructorInsertValidator;
import gr.aueb.cf.scheduleapp.validator.InstructorUpdateValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class InstructorController {

    private final IInstructorService instructorService;
    private final ISpecInstrService specInstrService;
    private final ISessionService sessionService;
    private final IEnrollmentService enrollmentService;
    private final InstructorInsertValidator instructorInsertValidator;
    private final InstructorUpdateValidator instructorUpdateValidator;

    @Autowired
    public InstructorController(IInstructorService instructorService, ISpecInstrService specInstrService, ISessionService sessionService, IEnrollmentService enrollmentService, InstructorInsertValidator instructorInsertValidator, InstructorUpdateValidator instructorUpdateValidator) {
        this.instructorService = instructorService;
        this.specInstrService = specInstrService;
        this.sessionService = sessionService;
        this.enrollmentService = enrollmentService;
        this.instructorInsertValidator = instructorInsertValidator;
        this.instructorUpdateValidator = instructorUpdateValidator;
    }

    @GetMapping("/instructor/insert")
    public String showInstructorForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("instructorInsertDTO", new InstructorInsertDTO());
        return "instructorInsertForm";
    }

    @PostMapping("/instructor/insert")
    public String addInstructor(@Valid InstructorInsertDTO instructorInsertDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        instructorInsertValidator.validate(instructorInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "instructorInsertForm";
        }
        try {
            instructorService.insertInstructor(instructorInsertDTO);

        } catch (Exception e) {
            return "instructorInsertForm";
        }

        return "redirect:/instructor/search";
    }

    @GetMapping("/instructor/search")
    public String showInstructorSearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "instructorSearchForm";
    }

    @PostMapping("/instructor/search")
    public String searchInstructor(@RequestParam("lastname") String lastName, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Instructor> instructors = instructorService.getInstructorsByLastname(lastName);
            model.addAttribute("instructors", instructors);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "instructorSearchForm";
    }

    @GetMapping("/instructor/edit")
    public String showEditInstructorForm(@RequestParam("id") Long instructorId, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        Instructor instructor = null;
        try {
            instructor = instructorService.getInstructorById(instructorId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("instructor", instructor);
        return "instructorEditForm";
    }

    @PostMapping("/instructor/edit")
    public String updateInstructor(@Valid InstructorUpdateDTO updateDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        instructorUpdateValidator.validate(updateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "instructorInsertForm";
        }
        try {
            instructorService.updateInstructor(updateDTO);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/instructor/search";
    }

    @PostMapping("/instructor/delete")
    public String deleteInstructor(@RequestParam("id") Long instructorId, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Session> sessions = sessionService.getSessionsByInstrId(instructorId);
            for (Session session : sessions) {
                List<Enrollment> enrollments = enrollmentService.getEnrollmentsByRoomIdDayIdAndTimes(session.getRoomId(), session.getDayId(), session.getStartingTime(), session.getEndingTime());
                for (Enrollment enrollment : enrollments) {
                    EnrollmentDTO enrollmentDTO = new EnrollmentDTO(enrollment.getClientId(), enrollment.getRoomId(), enrollment.getDayId(), enrollment.getStartingTime(), enrollment.getEndingTime());
                    enrollmentService.deleteEnrollment(enrollmentDTO);
                }
                SessionDTO sessionDTO = new SessionDTO(session.getRoomId(), session.getDayId(), session.getInstrId(), session.getSpecId(), session.getStartingTime(), session.getEndingTime());
                sessionService.deleteSession(sessionDTO);
            }
            List<SpecInstr> specInstrs = specInstrService.getSpecInstrsByInstrId(instructorId);
            for (SpecInstr specInstr : specInstrs) {
                SpecInstrDTO specInstrDTO = new SpecInstrDTO(specInstr.getInstructorId(), specInstr.getSpecialtyId());
                specInstrService.deleteSpecInstr(specInstrDTO);
            }
            instructorService.deleteInstructor(instructorId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/instructor/search";
    }



}
