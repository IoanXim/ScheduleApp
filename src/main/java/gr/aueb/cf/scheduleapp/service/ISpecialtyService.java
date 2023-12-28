package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.SpecialtyInsertDTO;
import gr.aueb.cf.scheduleapp.dto.SpecialtyUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Specialty;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ISpecialtyService {

    Specialty insertSpecialty(SpecialtyInsertDTO specialtyInsertDTO) throws Exception;
    Specialty updateSpecialty(SpecialtyUpdateDTO specialtyUpdateDTO) throws EntityNotFoundException;
    Specialty deleteSpecialty(Long id) throws EntityNotFoundException;
    List<Specialty> getAllSpecialties() throws Exception;
    List<Specialty> getSpecialtiesByName(String name) throws EntityNotFoundException;
    Specialty getSpecialtyById(Long id) throws EntityNotFoundException;
}
