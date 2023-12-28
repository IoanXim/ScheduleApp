package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.model.Enrollment;
import gr.aueb.cf.scheduleapp.repository.EnrollmentRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EnrollmentServiceImpl implements IEnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    @Override
    public Enrollment insertEnrollment(EnrollmentDTO enrollmentDTO) throws Exception {
        Enrollment enrollment = null;
        try {
            enrollment = enrollmentRepository.save(convertEnrollmentDtoToEnrollment(enrollmentDTO));
            if (enrollment.getClientId() == null || enrollment.getRoomId() == null || enrollment.getDayId() == null || enrollment.getStartingTime() == null || enrollment.getEndingTime() == null) {
                throw new Exception("Enrollment Insert Error");
            }
        } catch (Exception e) {
            log.info("Enrollment insert error");
            throw e;
        }
        return enrollment;
    }
    @Transactional
    @Override
    public Enrollment deleteEnrollment(EnrollmentDTO enrollmentDTO) throws EntityNotFoundException {
        Enrollment enrollment = null;
        try {
            enrollment = enrollmentRepository.findEnrollmentByClientIdAndRoomIdAndDayIdAndStartingTimeAndEndingTime(enrollmentDTO.getClientId(), enrollmentDTO.getRoomId(), enrollmentDTO.getDayId(), enrollmentDTO.getStartingTime(), enrollmentDTO.getEndingTime());
            enrollmentRepository.delete(enrollment);
            if (enrollment == null) {
                throw new EntityNotFoundException(Enrollment.class, 0L);
            }
        } catch (EntityNotFoundException e) {
            log.info("Error in enrollment delete");
            throw e;
        }
        return enrollment;
    }

    @Override
    public Enrollment getEnrollmentByClientIdRoomIdDayIdAndTimes(Long clientId, Long roomId, Long dayId, String startingTime, String endingTime) throws EntityNotFoundException {
        Enrollment enrollment = null;
        try {
            enrollment = enrollmentRepository.findEnrollmentByClientIdAndRoomIdAndDayIdAndStartingTimeAndEndingTime(clientId, roomId, dayId, startingTime, endingTime);
            if (enrollment == null) {
                throw new EntityNotFoundException(Enrollment.class, 0L);
            }
        } catch (EntityNotFoundException e) {
            log.info("Error in finding enrollment");
            throw e;
        }
        return enrollment;
    }

    @Override
    public List<Enrollment> getEnrollmentsByRoomIdDayIdAndTimes(Long roomId, Long dayId, String startingTime, String endingTime)  throws  Exception {
        List<Enrollment> enrollments;
        try {
            enrollments = enrollmentRepository.findEnrollmentsByRoomIdAndDayIdAndStartingTimeAndEndingTime(roomId, dayId, startingTime, endingTime);

        } catch (Exception e) {
            log.info("Error in finding enrollments");
            throw e;
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> getAllEnrollments() throws Exception {
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            enrollments = enrollmentRepository.findAll();
            if (enrollments.size() == 0) throw new Exception("No enrollments found");
        } catch (Exception e) {
            log.info("Error in retrieving all enrollments");
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> getEnrollmentsByClientId(Long id) throws EntityNotFoundException {
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            enrollments = enrollmentRepository.findEnrollmentsByClientId(id);
            if (enrollments.size() == 0) throw new EntityNotFoundException(Enrollment.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in retrieving enrollments for clientId: " + id);
        }
        return enrollments;
    }

    private Enrollment convertEnrollmentDtoToEnrollment(EnrollmentDTO enrollmentDTO) {
        return new Enrollment(enrollmentDTO.getClientId(), enrollmentDTO.getRoomId(), enrollmentDTO.getDayId(), enrollmentDTO.getStartingTime(), enrollmentDTO.getEndingTime());
    }
}
