package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.InstructorInsertDTO;
import gr.aueb.cf.scheduleapp.dto.InstructorUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Instructor;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;
public interface IInstructorService {

    Instructor insertInstructor(InstructorInsertDTO instructorInsertDTO) throws Exception;
    Instructor updateInstructor(InstructorUpdateDTO instructorUpdateDTO) throws EntityNotFoundException;
    Instructor deleteInstructor(Long id) throws EntityNotFoundException;
    List<Instructor> getInstructorsByLastname(String lastname) throws EntityNotFoundException;
    List<Instructor> getAllInstructors() throws Exception;
    Instructor getInstructorById(Long id) throws EntityNotFoundException;
    Instructor getInstructorBySsn(Long ssn) throws EntityNotFoundException;
}
