package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.service.IUserClientService;
import gr.aueb.cf.scheduleapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;


@Controller
public class AdminMenuController {

    private IUserService userService;

    @Autowired
    public AdminMenuController(IUserService userService, IUserClientService userClientService) {
        this.userService = userService;
    }


    @GetMapping("/admin/menu")
    public String showAdminMenuPage(HttpSession httpSession) {

        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "adminMenuPage";
    }
}
