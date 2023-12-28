package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.dto.SessionDTO;
import gr.aueb.cf.scheduleapp.dto.SpecInstrDTO;
import gr.aueb.cf.scheduleapp.model.*;
import gr.aueb.cf.scheduleapp.model.details.SpecInstrDetails;
import gr.aueb.cf.scheduleapp.service.*;
import gr.aueb.cf.scheduleapp.validator.SpecInstrValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SpecInstrController {

    private IInstructorService instructorService;
    private ISpecialtyService specialtyService;
    private ISessionService sessionService;
    private IEnrollmentService enrollmentService;
    private ISpecInstrService specInstrService;
    private SpecInstrValidator specInstrValidator;

    @Autowired
    public SpecInstrController(IInstructorService instructorService, ISpecialtyService specialtyService, ISessionService sessionService, IEnrollmentService enrollmentService, ISpecInstrService specInstrService, SpecInstrValidator specInstrValidator) {
        this.instructorService = instructorService;
        this.specialtyService = specialtyService;
        this.sessionService = sessionService;
        this.enrollmentService = enrollmentService;
        this.specInstrService = specInstrService;
        this.specInstrValidator = specInstrValidator;
    }

    @GetMapping("/specinstr/assign")
    public String showAssignSpecInstrForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Instructor> instructors = instructorService.getAllInstructors();
            List<Specialty> specialties = specialtyService.getAllSpecialties();
            model.addAttribute("instructors", instructors);
            model.addAttribute("specialties", specialties);
            model.addAttribute("specInstrDTO", new SpecInstrDTO());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "specInstrAssignForm";
    }

    @PostMapping("/specinstr/assign")
    public String assignSpecInstr(@Valid SpecInstrDTO specInstrDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        specInstrValidator.validate(specInstrDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/specinstr/assign";
        }
        try {
            specInstrService.insertSpecInstr(specInstrDTO);
        }catch (Exception e) {
            return "redirect:/specinstr/assign";
        }

        return "redirect:/specinstr/assign";
    }

    @GetMapping("/specinstr/search")
    public String showSpecInstrSearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "specInstrSearchForm";
    }

    @PostMapping("/specinstr/instructors/search")
    public String searchInstructorsByLastName(@RequestParam("lastname") String lastname, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Instructor> instructors = instructorService.getInstructorsByLastname(lastname);
            List<SpecInstrDetails> specInstrDetailsList = new ArrayList<>();

            for (Instructor instructor : instructors) {
                List<SpecInstr> tempSpecInstrList = specInstrService.getSpecInstrsByInstrId(instructor.getId());

                for (SpecInstr specInstr : tempSpecInstrList) {
                    Specialty specialty = specialtyService.getSpecialtyById(specInstr.getSpecialtyId());
                    SpecInstrDetails specInstrDetails = new SpecInstrDetails(instructor, specialty);
                    specInstrDetailsList.add(specInstrDetails);

                }
            }

            model.addAttribute("specInstrDetailsList", specInstrDetailsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            return "specInstrSearchResult";
    }

    @PostMapping("/specinstr/specialties/search")
    public String searchSpecialtiesByName(@RequestParam("name") String name, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Specialty> specialties = specialtyService.getSpecialtiesByName(name);
            List<SpecInstrDetails> specInstrDetailsList = new ArrayList<>();

            for (Specialty specialty : specialties) {
                List<SpecInstr> tempSpecInstrList = specInstrService.getSpecInstrsBySpecId(specialty.getId());

                for (SpecInstr specInstr : tempSpecInstrList) {
                    Instructor instructor = instructorService.getInstructorById(specInstr.getInstructorId());
                    SpecInstrDetails specInstrDetails = new SpecInstrDetails(instructor, specialty);
                    specInstrDetailsList.add(specInstrDetails);
                    System.out.println(specInstrDetails);
                }
            }

            model.addAttribute("specInstrDetailsList", specInstrDetailsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "specInstrSearchResult";
    }

    @PostMapping("/specinstr/delete")
    public String deleteSpecInstr(@RequestParam("instructorId") Long instructorId, @RequestParam("specialtyId") Long specialtyId, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            SpecInstrDTO specInstrDTO = new SpecInstrDTO(instructorId, specialtyId);
            List<Session> sessions = sessionService.getSessionsByInstrIdAndSpecId(instructorId, specialtyId);
            for (Session session : sessions) {
                List<Enrollment> enrollments = enrollmentService.getEnrollmentsByRoomIdDayIdAndTimes(session.getRoomId(), session.getDayId(), session.getStartingTime(), session.getEndingTime());
                for (Enrollment enrollment : enrollments) {
                    EnrollmentDTO enrollmentDTO = new EnrollmentDTO(enrollment.getClientId(), enrollment.getRoomId(), enrollment.getDayId(), enrollment.getStartingTime(), enrollment.getEndingTime());
                    enrollmentService.deleteEnrollment(enrollmentDTO);
                }
                SessionDTO sessionDTO = new SessionDTO(session.getRoomId(), session.getDayId(), session.getInstrId(), session.getSpecId(), session.getStartingTime(), session.getEndingTime());
                sessionService.deleteSession(sessionDTO);
            }
            specInstrService.deleteSpecInstr(specInstrDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/specinstr/search";
    }

}

