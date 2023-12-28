package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.*;
import gr.aueb.cf.scheduleapp.model.Enrollment;
import gr.aueb.cf.scheduleapp.model.Session;
import gr.aueb.cf.scheduleapp.model.SpecInstr;
import gr.aueb.cf.scheduleapp.model.Specialty;
import gr.aueb.cf.scheduleapp.service.IEnrollmentService;
import gr.aueb.cf.scheduleapp.service.ISessionService;
import gr.aueb.cf.scheduleapp.service.ISpecInstrService;
import gr.aueb.cf.scheduleapp.service.ISpecialtyService;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.scheduleapp.validator.SpecialtyInsertValidator;
import gr.aueb.cf.scheduleapp.validator.SpecialtyUpdateValidator;
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
public class SpecialtyController {
    private final ISpecialtyService specialtyService;
    private final ISpecInstrService specInstrService;
    private final ISessionService sessionService;
    private final IEnrollmentService enrollmentService;
    private final SpecialtyInsertValidator specialtyInsertValidator;
    private final SpecialtyUpdateValidator specialtyUpdateValidator;

    @Autowired
    public SpecialtyController(ISpecialtyService specialtyService, ISpecInstrService specInstrService, ISessionService sessionService, IEnrollmentService enrollmentService, SpecialtyInsertValidator specialtyInsertValidator, SpecialtyUpdateValidator specialtyUpdateValidator) {
        this.specialtyService = specialtyService;
        this.specInstrService = specInstrService;
        this.sessionService = sessionService;
        this.enrollmentService = enrollmentService;
        this.specialtyInsertValidator = specialtyInsertValidator;
        this.specialtyUpdateValidator = specialtyUpdateValidator;
    }

    @GetMapping("/specialty/insert")
    public String showSpecialtyForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("specialtyInsertDTO", new SpecialtyInsertDTO());
        return "specialtyInsertForm";
    }

    @PostMapping("/specialty/insert")
    public String addSpecialty(@Valid SpecialtyInsertDTO specialtyInsertDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        specialtyInsertValidator.validate(specialtyInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "specialtyInsertForm";
        }
        try {
            specialtyService.insertSpecialty(specialtyInsertDTO);

        } catch (Exception e) {
            return "specialtyInsertForm";
        }

        return "redirect:/specialty/search";
    }

    @GetMapping("/specialty/search")
    public String showSpecialtySearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "specialtySearchForm";
    }

    @PostMapping("/specialty/search")
    public String searchSpecialty(@RequestParam("name") String name, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Specialty> specialties = specialtyService.getSpecialtiesByName(name);
            model.addAttribute("specialties", specialties);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "specialtySearchForm";
    }

    @GetMapping("/specialty/edit")
    public String showEditSpecialtyForm(@RequestParam("id") Long specialtyId, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        Specialty specialty = null;
        try {
            specialty = specialtyService.getSpecialtyById(specialtyId);
            System.out.println(specialty);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("specialty", specialty);
        return "specialtyEditForm";
    }

    @PostMapping("/specialty/edit")
    public String updateSpecialty(@Valid SpecialtyUpdateDTO updateDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        specialtyUpdateValidator.validate(updateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/specialty/search";
        }
        try {
            specialtyService.updateSpecialty(updateDTO);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/specialty/search";
    }

    @PostMapping("/specialty/delete")
    public String deleteSpecialty(@RequestParam("id") Long specialtyId, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Session> sessions = sessionService.getSessionsBySpecId(specialtyId);
            for (Session session : sessions) {
                List<Enrollment> enrollments = enrollmentService.getEnrollmentsByRoomIdDayIdAndTimes(session.getRoomId(), session.getDayId(), session.getStartingTime(), session.getEndingTime());
                for (Enrollment enrollment : enrollments) {
                    EnrollmentDTO enrollmentDTO = new EnrollmentDTO(enrollment.getClientId(), enrollment.getRoomId(), enrollment.getDayId(), enrollment.getStartingTime(), enrollment.getEndingTime());
                    enrollmentService.deleteEnrollment(enrollmentDTO);
                }
                SessionDTO sessionDTO = new SessionDTO(session.getRoomId(), session.getDayId(), session.getInstrId(), session.getSpecId(), session.getStartingTime(), session.getEndingTime());
                sessionService.deleteSession(sessionDTO);
            }
            List<SpecInstr> specInstrs = specInstrService.getSpecInstrsBySpecId(specialtyId);
            for (SpecInstr specInstr : specInstrs) {
                SpecInstrDTO specInstrDTO = new SpecInstrDTO(specInstr.getInstructorId(), specInstr.getSpecialtyId());
                specInstrService.deleteSpecInstr(specInstrDTO);
            }
            specialtyService.deleteSpecialty(specialtyId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return "redirect:/specialty/search";
    }
}
