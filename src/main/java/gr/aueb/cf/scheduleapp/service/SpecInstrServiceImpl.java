package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.SpecInstrDTO;
import gr.aueb.cf.scheduleapp.model.SpecInstr;
import gr.aueb.cf.scheduleapp.repository.SpecInstrRepository;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SpecInstrServiceImpl implements ISpecInstrService {

    private final SpecInstrRepository specInstrRepository;

    @Autowired
    public SpecInstrServiceImpl(SpecInstrRepository specInstrRepository) {
        this.specInstrRepository = specInstrRepository;
    }

    @Transactional
    @Override
    public SpecInstr insertSpecInstr(SpecInstrDTO specInstrDTO) throws Exception {
        SpecInstr specInstr = null;
        try {
            specInstr = specInstrRepository.save(convertSpecInstrDtoToSpecInstr(specInstrDTO));
            if (specInstr.getInstructorId() == null || specInstr.getSpecialtyId() == null) throw new Exception("Insert Error");
        } catch (Exception e) {
            log.info("SpecInstr Insert Error");
            throw e;
        }
        return specInstr;
    }

    @Transactional
    @Override
    public SpecInstr deleteSpecInstr(SpecInstrDTO specInstrDTO) throws Exception {
        SpecInstr specInstr = null;

        try {
            specInstr = specInstrRepository.findSpecInstrByInstructorIdAndSpecialtyId(specInstrDTO.getInstrId(), specInstrDTO.getSpecId());
            if (specInstr == null) throw new Exception("SpecInstr with instrId " + specInstrDTO.getInstrId() + " and specId " + specInstrDTO.getSpecId() + " not found");
            specInstrRepository.delete(specInstr);
        } catch (Exception e) {
            log.info("Error in SpecInstr deletion");
            throw e;
        }
        return specInstr;
    }

    @Override
    public List<SpecInstr> getAllSpecInstrs() throws Exception {
        List<SpecInstr> specInstrs = new ArrayList<>();
        try {
            specInstrs = specInstrRepository.findAll();
            if (specInstrs.size() == 0) throw new Exception("no SpecInstrs found");
        } catch (Exception e) {
            log.info("Error in get all SpecInstrs");
        }
        return specInstrs;
    }

    @Override
    public List<SpecInstr> getSpecInstrsByInstrId(Long id) throws Exception {
        List<SpecInstr> specInstrs = new ArrayList<>();
        try {
            specInstrs = specInstrRepository.findByInstructorId(id);
            if (specInstrs.size() == 0) throw new Exception("no SpecInstrs with instrId " + id + " found");
        } catch (Exception e) {
            log.info("Error in get SpecInstrs by instrId: " + id);
        }
        return specInstrs;
    }

    @Override
    public List<SpecInstr> getSpecInstrsBySpecId(Long id) throws Exception {
        List<SpecInstr> specInstrs = new ArrayList<>();
        try {
            specInstrs = specInstrRepository.findBySpecialtyId(id);
            if (specInstrs.size() == 0) throw new Exception("no SpecInstrs with specId " + id + " found");
        } catch (Exception e) {
            log.info("Error in get SpecInstrs by specId: " + id);
        }
        return specInstrs;
    }

    @Override
    public  SpecInstr getSpecInstrByInstrIdAndSpecId(Long instrId, Long specId) throws Exception {
        SpecInstr specInstr;
        try {
            specInstr = specInstrRepository.findSpecInstrByInstructorIdAndSpecialtyId(instrId, specId);
            if (specInstr == null) throw new Exception("SpecInstr with instrId " + instrId + " and specId " + specId + " not found");
        } catch (Exception e) {
            log.info("Error in SpecInstr search with instrId, specId : " + instrId + ", " + specId);
            throw e;
        }
        return specInstr;
    }

    private SpecInstr convertSpecInstrDtoToSpecInstr(SpecInstrDTO specInstrDTO) {
        return new SpecInstr(specInstrDTO.getInstrId(), specInstrDTO.getSpecId());
    }
}
