package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.ClientInsertDTO;
import gr.aueb.cf.scheduleapp.dto.ClientUpdateDTO;
import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.dto.UserClientDTO;
import gr.aueb.cf.scheduleapp.model.Client;
import gr.aueb.cf.scheduleapp.model.Enrollment;
import gr.aueb.cf.scheduleapp.model.UserClient;
import gr.aueb.cf.scheduleapp.service.IClientService;
import gr.aueb.cf.scheduleapp.service.IEnrollmentService;
import gr.aueb.cf.scheduleapp.service.IUserClientService;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.scheduleapp.validator.ClientInsertValidator;
import gr.aueb.cf.scheduleapp.validator.ClientUpdateValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class ClientController {

    private final IClientService clientService;
    private final IEnrollmentService enrollmentService;
    private final IUserClientService userClientService;
    private final ClientInsertValidator clientInsertValidator;
    private final ClientUpdateValidator clientUpdateValidator;

    @Autowired
    public ClientController(IClientService clientService, IEnrollmentService enrollmentService, IUserClientService userClientService, ClientInsertValidator clientInsertValidator, ClientUpdateValidator clientUpdateValidator) {
        this.clientService = clientService;
        this.enrollmentService = enrollmentService;
        this.userClientService = userClientService;
        this.clientInsertValidator = clientInsertValidator;
        this.clientUpdateValidator = clientUpdateValidator;
    }

    @GetMapping("/client/insert")
    public String showClientForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("clientInsertDTO", new ClientInsertDTO());
        return "clientInsertForm";
    }

    @PostMapping("/client/insert")
    public String addClient(@Valid ClientInsertDTO clientInsertDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        clientInsertValidator.validate(clientInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "clientInsertForm";
        }
        try {
            clientService.insertClient(clientInsertDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/client/search";
    }

    @GetMapping("/client/search")
    public  String showClientSearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "clientSearchForm";
    }

    @PostMapping("/client/search")
    public String searchClient(@RequestParam("lastname") String lastname, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Client> clients = clientService.getClientsByLastname(lastname);
            model.addAttribute("clients", clients);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "clientSearchForm";
    }

    @GetMapping("/client/edit")
    public String showEditClientForm(@RequestParam("id") Long clientId, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        Client client = null;
        try {
            client = clientService.getClientById(clientId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("client", client);
        return "clientEditForm";
    }

    @PostMapping("/client/edit")
    public String updateClient(@Valid ClientUpdateDTO updateDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        clientUpdateValidator.validate(updateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "clientEditForm";
        }
        try {
            clientService.updateClient(updateDTO);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/client/search";
    }

    @PostMapping("/client/delete")
    public String deleteClient(@RequestParam("id") Long clientId, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            UserClient userClient = userClientService.getUserClientByClientId(clientId);
            if (userClient != null) {
                UserClientDTO userClientDTO = new UserClientDTO(userClient.getUserId(), userClient.getClientId());
                userClientService.deleteUserClient(userClientDTO);
            }
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByClientId(clientId);
            for (Enrollment enrollment : enrollments) {
                EnrollmentDTO enrollmentDTO = new EnrollmentDTO(enrollment.getClientId(), enrollment.getRoomId(), enrollment.getDayId(), enrollment.getStartingTime(), enrollment.getEndingTime());
                enrollmentService.deleteEnrollment(enrollmentDTO);
            }
            clientService.deleteClient(clientId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/client/search";
    }
}
