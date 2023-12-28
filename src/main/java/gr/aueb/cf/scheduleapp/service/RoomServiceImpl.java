package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.RoomDTO;
import gr.aueb.cf.scheduleapp.model.Room;
import gr.aueb.cf.scheduleapp.repository.RoomRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    @Override
    public Room insertRoom(RoomDTO roomDTO) throws Exception {
        Room room = null;
        try {
            room = roomRepository.save(convertRoomDtoToRoom(roomDTO));
        } catch (Exception e) {
            log.info("Room Insert Error");
            throw e;
        }
        return room;
    }

    @Transactional
    @Override
    public Room deleteRoom(RoomDTO roomDTO) throws EntityNotFoundException {
        Room room = null;

        try {
            room = roomRepository.findRoomById(roomDTO.getId());
            if (room == null) throw new EntityNotFoundException(Room.class, roomDTO.getId());
            roomRepository.delete(room);
        } catch (EntityNotFoundException e) {
            log.info("Error in room delete");
            throw e;
        }
        return room;
    }

    @Override
    public List<Room> getAllRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        try {
            rooms = roomRepository.findAll();
            if (rooms.size() == 0) throw new Exception("no rooms found");
        } catch (Exception e) {
            log.info("Error in get all rooms");
        }
        return rooms;
    }

    @Override
    public Room getRoomById(Long id) throws EntityNotFoundException {
        Room room = null;
        try {
            room = roomRepository.findRoomById(id);
            if (room == null) throw new EntityNotFoundException(Room.class, id);
        } catch (EntityNotFoundException e) {
            log.info("Could not find room with id: " + id);
        }
        return room;
    }
    private Room convertRoomDtoToRoom(RoomDTO roomDTO) {
        return new Room(roomDTO.getId());
    }

}
