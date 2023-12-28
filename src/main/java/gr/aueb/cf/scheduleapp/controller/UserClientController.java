package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.UserClientDTO;
import gr.aueb.cf.scheduleapp.model.Client;
import gr.aueb.cf.scheduleapp.model.User;
import gr.aueb.cf.scheduleapp.model.UserClient;
import gr.aueb.cf.scheduleapp.model.details.UserClientDetails;
import gr.aueb.cf.scheduleapp.service.IClientService;
import gr.aueb.cf.scheduleapp.service.IUserClientService;
import gr.aueb.cf.scheduleapp.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserClientController {

    private IUserService userService;
    private IClientService clientService;
    private IUserClientService userClientService;

    @Autowired
    public UserClientController(IUserService userService, IClientService clientService, IUserClientService userClientService) {
        this.userService = userService;
        this.clientService = clientService;
        this.userClientService = userClientService;
    }

    @GetMapping("/userclient/assign")
    public String showAssignUserClientForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<User> users = userService.getAllUsers();
            List<Client> clients = clientService.getAllClients();
            model.addAttribute("users", users);
            model.addAttribute("clients", clients);
            model.addAttribute("userClientDTO", new UserClientDTO());
        } catch (Exception e) {
            return "userClientAssignForm";
        }
        return "userClientAssignForm";
    }

    @PostMapping("/userclient/assign")
    public String assignUserClient(@ModelAttribute UserClientDTO userClientDTO, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            userClientService.insertUserClient(userClientDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/userclient/assign";
    }

    @GetMapping("/userclient/search")
    public String showUserClientSearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "userClientSearchForm";
    }

    @PostMapping("userclient/users/search")
    public String searchUsersByUserName(@RequestParam("username") String username, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<User> users = userService.getUsersByUsername(username);
            List<UserClientDetails> userClientDetailsList = new ArrayList<>();
            for (User user : users) {
                UserClient userClient = userClientService.getUserClientByUserId(user.getId());
                if (userClient == null) continue;
                Client client = clientService.getClientById(userClient.getClientId());
                UserClientDetails userClientDetails = new UserClientDetails(user, client);
                userClientDetailsList.add(userClientDetails);

            }

            model.addAttribute("userClientDetailsList", userClientDetailsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "userClientSearchResult";
    }

    @PostMapping("userclient/clients/search")
    public String searchClientsByLastname(@RequestParam("lastname") String lastname, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Client> clients = clientService.getClientsByLastname(lastname);
            List<UserClientDetails> userClientDetailsList = new ArrayList<>();

            for (Client client : clients) {
                UserClient userClient = userClientService.getUserClientByClientId(client.getId());
                if (userClient == null) continue;
                User user = userService.getUserById(userClient.getUserId());
                UserClientDetails userClientDetails = new UserClientDetails(user, client);
                userClientDetailsList.add(userClientDetails);

            }

            model.addAttribute("userClientDetailsList", userClientDetailsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "userClientSearchResult";
    }

    @PostMapping("userclient/delete")
    public String deleteUserClient(@RequestParam("userId") Long userId, @RequestParam("clientId") Long clientId, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            UserClientDTO userClientDTO = new UserClientDTO(userId, clientId);
            userClientService.deleteUserClient(userClientDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/userclient/search";
    }

}
