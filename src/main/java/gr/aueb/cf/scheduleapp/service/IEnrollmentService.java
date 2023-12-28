package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.model.Enrollment;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IEnrollmentService {

    Enrollment insertEnrollment(EnrollmentDTO enrollmentDTO) throws Exception;
    Enrollment deleteEnrollment(EnrollmentDTO enrollmentDTO) throws EntityNotFoundException;
    Enrollment getEnrollmentByClientIdRoomIdDayIdAndTimes(Long clientId, Long roomId, Long dayId, String startingTime, String endingTime) throws EntityNotFoundException;
    List<Enrollment> getEnrollmentsByRoomIdDayIdAndTimes(Long roomId, Long dayId, String startingTime, String endingTime) throws Exception;
    List<Enrollment> getAllEnrollments() throws Exception;
    List<Enrollment> getEnrollmentsByClientId(Long id) throws EntityNotFoundException;
}
