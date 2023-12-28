package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.*;
import gr.aueb.cf.scheduleapp.model.User;
import gr.aueb.cf.scheduleapp.model.UserClient;
import gr.aueb.cf.scheduleapp.service.IClientService;
import gr.aueb.cf.scheduleapp.service.IUserClientService;
import gr.aueb.cf.scheduleapp.service.IUserService;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.scheduleapp.validator.UserInsertValidator;
import gr.aueb.cf.scheduleapp.validator.UserUpdateValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
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
public class UserController {

    private final IUserService userService;
    private final IUserClientService userClientService;
    private final UserInsertValidator userInsertValidator;
    private final UserUpdateValidator userUpdateValidator;

    @Autowired
    public UserController(IUserService userService, IUserClientService userClientService, UserInsertValidator userInsertValidator, UserUpdateValidator userUpdateValidator) {
        this.userService = userService;
        this.userClientService = userClientService;
        this.userInsertValidator = userInsertValidator;
        this.userUpdateValidator = userUpdateValidator;
    }





    @GetMapping("/user/insert")
    public String showUserForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            model.addAttribute("userInsertDTO", new UserInsertDTO());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "userInsertForm";
    }

    @PostMapping("/user/insert")
    public String addUser(@Valid UserInsertDTO userInsertDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        userInsertValidator.validate(userInsertDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "userInsertForm";
        }
        try {
            int workload = 12;
            String salt = BCrypt.gensalt(workload);
            String hashedPassword = BCrypt.hashpw(userInsertDTO.getPassword(), salt);
            userInsertDTO.setPassword(hashedPassword);
            userService.insertUser(userInsertDTO);
        } catch (Exception e) {
            return "userInsertForm";
        }
        return "redirect:/user/search";
    }

    @GetMapping("/user/search")
    public  String showUserSearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "userSearchForm";
    }

    @PostMapping("/user/search")
    public String searchUser(@RequestParam("username") String username, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<User> users = userService.getUsersByUsername(username);
            model.addAttribute("users", users);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "userSearchForm";
    }

    @GetMapping("/user/edit")
    public String showEditUserForm(@RequestParam("username") String username, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        User user = null;
        try {
            user = userService.getUserByUsername(username);
        } catch (EntityNotFoundException e) {
            return "userEditForm";
        }
        model.addAttribute("user", user);
        return "userEditForm";
    }

    @PostMapping("/user/edit")
    public String updateUser(@Valid UserUpdateDTO userUpdateDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        userUpdateValidator.validate(userUpdateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "userEditForm";
        }
        try {
            int workload = 12;
            String salt = BCrypt.gensalt(workload);
            String hashedPassword = BCrypt.hashpw(userUpdateDTO.getPassword(), salt);
            userUpdateDTO.setPassword(hashedPassword);
            userService.updateUser(userUpdateDTO);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/user/search";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam("id") Long userId, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            if (userId == 1) {
                return "redirect:/user/search";
            }
            UserClient userClient = userClientService.getUserClientByUserId(userId);
            if (userClient != null) {
                UserClientDTO userClientDTO = new UserClientDTO(userClient.getUserId(), userClient.getClientId());
                userClientService.deleteUserClient(userClientDTO);
            }
            userService.deleteUser(userId);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/user/search";
    }

}
