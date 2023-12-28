package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.SessionDTO;
import gr.aueb.cf.scheduleapp.model.Session;
import gr.aueb.cf.scheduleapp.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SessionServiceImpl implements ISessionService {

    private final SessionRepository sessionRepository;

    @Autowired

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional
    @Override
    public Session insertSession(SessionDTO sessionDTO) throws Exception {
        Session session = null;
        try {
            session = sessionRepository.save(convertSessionDtoToSession(sessionDTO));
            if (session.getRoomId() == null || session.getDayId() == null || session.getInstrId() == null || session.getSpecId() == null || session.getStartingTime() == null || session.getEndingTime() == null) {
                throw new Exception("Insert session Error");
            }
        } catch (Exception e) {
            log.info("Insert session Error");
            throw e;
        }
        return session;
    }

    @Transactional
    @Override
    public Session deleteSession(SessionDTO sessionDTO) throws Exception {
        Session session = null;
        try {
            session = sessionRepository.findSessionByRoomIdAndDayIdAndStartingTimeAndEndingTime(sessionDTO.getRoomId(), sessionDTO.getDayId(), sessionDTO.getStartingTime(), sessionDTO.getEndingTime());
            sessionRepository.delete(session);
            if (session == null) {
                throw new Exception("Session: " + convertSessionDtoToSession(sessionDTO) + " not found");
            }
        } catch (Exception e) {
            log.info("Error in session delete");
            throw e;
        }
        return session;
    }

    public Session getSessionByRoomIdDayIdAndTimes(Long roomId, Long dayId, String startingTime, String endingTime) throws Exception {
        Session session = null;
        try {
            session = sessionRepository.findSessionByRoomIdAndDayIdAndStartingTimeAndEndingTime(roomId, dayId, startingTime, endingTime);
            if (session == null) {
                throw new Exception("Session with roomId, dayId, startingTime, endingTime " + roomId + ", " + dayId + ", " + startingTime + ", " + endingTime + " not found");
            }
        } catch (Exception e) {
            log.info("Error in retrieving session");
        }
        return session;
    }

    public List<Session> getAllSessions() throws Exception {
        List<Session> sessions = new ArrayList<>();
        try {
            sessions = sessionRepository.findAll();
            if (sessions.size() == 0) throw new Exception("Sessions not found");
        } catch (Exception e) {
            log.info("Error in retrieving all sessions");
        }
        return sessions;
    }
    public List<Session> getSessionsByRoomId(Long id) throws Exception {
        List<Session> sessions = new ArrayList<>();
        try {
            sessions = sessionRepository.findSessionsByRoomId(id);
            if (sessions.size() == 0) throw new Exception("Sessions with roomId: " + id + " not found");
        } catch (Exception e) {
            log.info("Error in retrieving sessions by roomId");
        }
        return sessions;
    }

    public List<Session> getSessionsBySpecId(Long id) throws Exception {
        List<Session> sessions = new ArrayList<>();
        try {
            sessions = sessionRepository.findSessionsBySpecId(id);
            if (sessions.size() == 0) throw new Exception("Sessions with specId: " + id + " not found");
        } catch (Exception e) {
            log.info("Error in retrieving sessions by specId");
        }
        return sessions;
    }

    public List<Session> getSessionsByInstrId(Long id) throws Exception {
        List<Session> sessions = new ArrayList<>();
        try {
            sessions = sessionRepository.findSessionsByInstrId(id);
            if (sessions.size() == 0) throw new Exception("Sessions with instrId: " + id + " not found");
        } catch (Exception e) {
            log.info("Error in retrieving sessions by instrId");
        }
        return sessions;
    }

    public List<Session> getSessionsByInstrIdAndSpecId(Long instrId, Long specId) throws Exception {
        List<Session> sessions = new ArrayList<>();
        try {
            sessions = sessionRepository.findSessionsByInstrIdAndSpecId(instrId, specId);
            if (sessions.size() == 0) throw new Exception("Sessions with instrId and specId: " + instrId + " " + specId +  "not found");
        } catch (Exception e) {
            log.info("Error in retrieving sessions by instrId");
        }
        return sessions;
    }

    private Session convertSessionDtoToSession(SessionDTO sessionDTO) {
        return new Session(sessionDTO.getRoomId(), sessionDTO.getDayId(), sessionDTO.getInstrId(), sessionDTO.getSpecId(), sessionDTO.getStartingTime(), sessionDTO.getEndingTime());
    }
}
