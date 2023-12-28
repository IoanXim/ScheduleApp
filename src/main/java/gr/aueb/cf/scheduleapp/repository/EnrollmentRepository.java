package gr.aueb.cf.scheduleapp.repository;

import gr.aueb.cf.scheduleapp.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findEnrollmentsByRoomId(Long id);
    List<Enrollment> findEnrollmentsByDayId(Long id);
    List<Enrollment> findEnrollmentsByClientId(Long id);
    List<Enrollment> findEnrollmentsByRoomIdAndDayIdAndStartingTimeAndEndingTime(Long roomId, Long dayId, String startingTime, String endingTime);
    Enrollment findEnrollmentByClientIdAndRoomIdAndDayIdAndStartingTimeAndEndingTime(Long clientId, Long roomId, Long dayId, String startingTime, String endingTime);

}
