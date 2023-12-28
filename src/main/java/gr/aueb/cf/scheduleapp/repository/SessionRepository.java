package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findSessionsByRoomId(Long id);
    List<Session> findSessionsByDayId(Long id);
    List<Session> findSessionsByDayIdAndRoomId(Long dayId, Long roomId);
    List<Session> findSessionsBySpecId(Long specId);
    List<Session> findSessionsByInstrId(Long specId);
    List<Session> findSessionsByInstrIdAndSpecId(Long instrId, Long specId);
    Session findSessionByRoomIdAndDayIdAndStartingTimeAndEndingTime
            (Long roomId, Long dayId,
             String startingTime, String endingTime);

}
