package gr.aueb.cf.scheduleapp.controller;

import gr.aueb.cf.scheduleapp.dto.RoomDTO;
import gr.aueb.cf.scheduleapp.model.Room;
import gr.aueb.cf.scheduleapp.service.IRoomService;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.scheduleapp.validator.RoomValidator;
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
public class RoomController {

    private final IRoomService roomService;
    private final RoomValidator roomValidator;


    @Autowired
    public RoomController(IRoomService roomService, RoomValidator roomValidator) {
        this.roomService = roomService;
        this.roomValidator = roomValidator;
    }

    @GetMapping("/room/insert")
    public String showRoomInsertForm(Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        model.addAttribute("roomDTO", new RoomDTO());
        return "roomInsertForm";
    }

    @PostMapping("room/insert")
    public String addRoom(@Valid RoomDTO roomDTO, BindingResult bindingResult, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        roomValidator.validate(roomDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "roomInsertForm";
        }
        try {
            roomService.insertRoom(roomDTO);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return "redirect:/room/search";
    }

    @GetMapping("/room/search")
    public String showRoomSearchForm(HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        return "roomSearchForm";
    }

    @PostMapping("/room/search")
    public String searchRoom(@RequestParam(value = "id", required = false) Long id, Model model, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            List<Room> rooms = new ArrayList<>();
            if (id != null) {
                rooms.add(roomService.getRoomById(id));
                model.addAttribute("rooms", rooms);
            } else {
                rooms = roomService.getAllRooms();
                model.addAttribute("rooms", rooms);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "roomSearchForm";
    }

    @GetMapping("room/delete")
    public String deleteRoom(@RequestParam("id") Long id, HttpSession httpSession) {
        if(httpSession.getAttribute("admin") == null || !((boolean) httpSession.getAttribute("admin"))) {
            httpSession.invalidate();
            return "redirect:/login";
        }
        try {
            RoomDTO roomDTO = new RoomDTO(id);
            roomService.deleteRoom(roomDTO);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/room/search";
    }
}


