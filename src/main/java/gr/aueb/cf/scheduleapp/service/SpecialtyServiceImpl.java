package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.SpecialtyInsertDTO;
import gr.aueb.cf.scheduleapp.dto.SpecialtyUpdateDTO;
import gr.aueb.cf.scheduleapp.model.Specialty;
import gr.aueb.cf.scheduleapp.repository.SpecialtyRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SpecialtyServiceImpl implements ISpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Transactional
    @Override
    public Specialty insertSpecialty(SpecialtyInsertDTO specialtyInsertDTO) throws Exception {
        Specialty specialty = null;
        try {
            specialty = specialtyRepository.save(convertInsertDtoToSpecialty(specialtyInsertDTO));
            if (specialty.getId() == null) throw new Exception("Insert Error");
        } catch (Exception e) {
            log.info("Insert Error");
            throw e;
        }
        return specialty;
    }

    @Transactional
    @Override
    public Specialty updateSpecialty(SpecialtyUpdateDTO specialtyUpdateDTO) throws EntityNotFoundException {
        Specialty specialty = null;
        Specialty updatedSpecialty = null;
        try {
            specialty = specialtyRepository.findSpecialtyById(specialtyUpdateDTO.getId());
            if (specialty == null) throw new EntityNotFoundException(Specialty.class, specialtyUpdateDTO.getId());
            updatedSpecialty = specialtyRepository.save(convertUpdateDtoToSpecialty(specialtyUpdateDTO));
        } catch (EntityNotFoundException e) {
            log.info("Error in update");
            throw e;
        }
        return updatedSpecialty;
    }

    @Transactional
    @Override
    public Specialty deleteSpecialty(Long id) throws EntityNotFoundException {
        Specialty specialty = null;

        try {
            specialty = specialtyRepository.findSpecialtyById(id);
            if (specialty == null) throw new EntityNotFoundException(Specialty.class, id);
            specialtyRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.info("Error in Update");
            throw e;
        }
        return specialty;
    }

    @Override
    public List<Specialty> getAllSpecialties() throws Exception {
        List<Specialty> specialties = new ArrayList<>();
        try {
            specialties = specialtyRepository.findAll();
            if (specialties.size() == 0) throw new EntityNotFoundException(Specialty.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get all specialties");
            throw e;
        }
        return specialties;
    }
    @Override
    public List<Specialty> getSpecialtiesByName(String name) throws EntityNotFoundException{
        List<Specialty> specialties = new ArrayList<>();
        try {
            specialties = specialtyRepository.findByNameStartingWith(name);
            if (specialties.size() == 0) throw new EntityNotFoundException(Specialty.class, 0L);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get specialties by name");
            throw e;
        }
        return specialties;
    }

    @Override
    public Specialty getSpecialtyById(Long id) throws EntityNotFoundException {
        Specialty specialty;
        try {
            specialty = specialtyRepository.findSpecialtyById(id);
            if (specialty == null) throw new EntityNotFoundException(Specialty.class, id);
        } catch (EntityNotFoundException e) {
            log.info("Error in Get specialty by id");
            throw e;
        }
        return specialty;
    }

    private Specialty convertInsertDtoToSpecialty(SpecialtyInsertDTO specialtyInsertDTO) {
        return new Specialty(null, specialtyInsertDTO.getName());
    }

    private Specialty convertUpdateDtoToSpecialty(SpecialtyUpdateDTO specialtyUpdateDTO) {
        return new Specialty(specialtyUpdateDTO.getId(), specialtyUpdateDTO.getName());
    }
}
