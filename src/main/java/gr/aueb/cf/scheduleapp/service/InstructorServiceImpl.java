package gr.aueb.cf.scheduleapp.service;


import gr.aueb.cf.scheduleapp.dto.InstructorInsertDTO;
import gr.aueb.cf.scheduleapp.dto.InstructorUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Instructor;
import gr.aueb.cf.scheduleapp.repository.InstructorRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InstructorServiceImpl implements IInstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository) { this.instructorRepository = instructorRepository; }

    @Transactional
    @Override
    public Instructor insertInstructor(InstructorInsertDTO instructorInsertDTO) throws Exception {
        Instructor instructor = null;
        try {
            instructor = instructorRepository.save(convertInsertDtoToInstructor(instructorInsertDTO));
            if (instructor.getId() == null) throw new Exception("Insert Error");
        } catch (Exception e) {
            log.info("Insert Instructor Error");
            throw e;
        }
        return instructor;
    }
    @Transactional
    @Override
    public Instructor updateInstructor(InstructorUpdateDTO instructorUpdateDTO) throws EntityNotFoundException {
        Instructor instructor = null;
        Instructor updatedInstructor = null;
        try {
            instructor = instructorRepository.findInstructorById(instructorUpdateDTO.getId());
            if (instructor == null) throw new EntityNotFoundException(Instructor.class, instructorUpdateDTO.getId());
            updatedInstructor = instructorRepository.save(convertUpdateDtoToInstructor(instructorUpdateDTO));
        } catch (EntityNotFoundException e) {
            log.info("Error in instructor update");
            throw e;
        }
        return updatedInstructor;
    }

    @Transactional
    @Override
    public Instructor deleteInstructor(Long id) throws EntityNotFoundException {
        Instructor instructor = null;

        try {
            instructor = instructorRepository.findInstructorById(id);
            if (instructor == null) throw new EntityNotFoundException(Instructor.class, id);
            instructorRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.info("Error in instructor Delete");
            throw e;
        }
        return instructor;
    }

    @Override
    public List<Instructor> getInstructorsByLastname(String lastname) throws EntityNotFoundException {
        List<Instructor> instructors = new ArrayList<>();
        try {
            instructors = instructorRepository.findByLastnameStartingWith(lastname);
            if (instructors.size() == 0) throw new EntityNotFoundException(Instructor.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get instructors by lastname");
            throw e;
        }
        return instructors;
    }

    @Override
    public List<Instructor> getAllInstructors() throws Exception {
        List<Instructor> instructors = new ArrayList<>();
        try {
            instructors = instructorRepository.findAll();
            if (instructors.size() == 0) throw new EntityNotFoundException(Instructor.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get all instructors");
            throw e;
        }
        return instructors;
    }

    @Override
    public Instructor getInstructorById(Long id) throws EntityNotFoundException {
        Instructor instructor;
        try {
            instructor = instructorRepository.findInstructorById(id);
            if (instructor == null) throw new EntityNotFoundException(Instructor.class, id);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get instructor by id");
            throw e;
        }
        return instructor;
    }

    @Override
    public Instructor getInstructorBySsn(Long ssn) throws EntityNotFoundException {
        Instructor instructor;
        try {
            instructor = instructorRepository.findInstructorBySsn(ssn);
            if (instructor == null) throw new EntityNotFoundException(Instructor.class, ssn);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get instructor by ssn");
            throw e;
        }
        return instructor;
    }


    private Instructor convertInsertDtoToInstructor(InstructorInsertDTO instructorInsertDTO) {
        return new Instructor(null, instructorInsertDTO.getFirstname(), instructorInsertDTO.getLastname(), instructorInsertDTO.getSsn());
    }

    private Instructor convertUpdateDtoToInstructor(InstructorUpdateDTO instructorUpdateDTO) {
        return new Instructor(instructorUpdateDTO.getId(), instructorUpdateDTO.getFirstname(), instructorUpdateDTO.getLastname(), instructorUpdateDTO.getSsn());
    }
}
