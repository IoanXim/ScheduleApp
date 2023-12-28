package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.RoomDTO;
import gr.aueb.cf.scheduleapp.model.Room;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IRoomService {

    Room insertRoom (RoomDTO roomDTO) throws Exception;

    Room deleteRoom (RoomDTO roomDTO) throws EntityNotFoundException;

    List<Room> getAllRooms() throws Exception;

    Room getRoomById(Long id) throws EntityNotFoundException;
}
