package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.UserInsertDTO;
import gr.aueb.cf.scheduleapp.model.User;
import gr.aueb.cf.scheduleapp.service.IUserService;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.scheduleapp.validator.UserInsertValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    private IUserService userService;
    private UserInsertValidator userInsertValidator;

    public LoginController(IUserService userService, UserInsertValidator userInsertValidator) {
        this.userService = userService;
        this.userInsertValidator = userInsertValidator;
    }

    @GetMapping("/")
    public String showPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, HttpSession httpSession) {
        httpSession.invalidate();
        try {
            model.addAttribute("userInsertDTO", new UserInsertDTO());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "LoginPage";
    }

    @PostMapping("/login")
    public String login(@Valid UserInsertDTO userInsertDTO, BindingResult bindingResult, HttpSession httpSession) {
        userInsertValidator.validate(userInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/login";
        }
        try {
            User user = userService.getUserByUsername(userInsertDTO.getUsername());
            String hashedPassword = user.getPassword();
            if (userInsertDTO.getUsername().equals("admin")) {
                if(BCrypt.checkpw(userInsertDTO.getPassword(), hashedPassword)) {
                    httpSession.setAttribute("admin", true);
                    httpSession.setAttribute("user", user);
                    return "redirect:/admin/menu";
                }
            }
            else if(BCrypt.checkpw(userInsertDTO.getPassword(), hashedPassword)) {
                httpSession.setAttribute("user", user);
                httpSession.setAttribute("admin", false);
                return "redirect:client/myschedule";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/login";
    }
}
