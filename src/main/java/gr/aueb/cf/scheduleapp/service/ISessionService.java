package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.SessionDTO;
import gr.aueb.cf.scheduleapp.model.Session;

import java.util.List;

public interface ISessionService {
    Session insertSession(SessionDTO sessionDTO) throws Exception;
    Session deleteSession(SessionDTO sessionDTO) throws Exception;
    Session getSessionByRoomIdDayIdAndTimes(Long roomId, Long dayId, String startingTime, String endingTime) throws Exception;

    List<Session> getAllSessions() throws Exception;
    List<Session> getSessionsByRoomId(Long id) throws Exception;
    List<Session> getSessionsBySpecId(Long id) throws Exception;
    List<Session> getSessionsByInstrId(Long id) throws Exception;
    List<Session> getSessionsByInstrIdAndSpecId(Long instrId, Long specId) throws Exception;
}
