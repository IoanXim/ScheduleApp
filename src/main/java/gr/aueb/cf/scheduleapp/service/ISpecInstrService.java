package gr.aueb.cf.scheduleapp.service;

import gr.aueb.cf.scheduleapp.dto.SpecInstrDTO;
import gr.aueb.cf.scheduleapp.model.SpecInstr;
import gr.aueb.cf.scheduleapp.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface ISpecInstrService {

    SpecInstr insertSpecInstr(SpecInstrDTO specInstrDTO) throws Exception;
    SpecInstr deleteSpecInstr(SpecInstrDTO specInstrDTO) throws Exception;
    List<SpecInstr> getAllSpecInstrs() throws Exception;
    List<SpecInstr> getSpecInstrsByInstrId(Long id) throws Exception;
    List<SpecInstr> getSpecInstrsBySpecId(Long id) throws Exception;
    SpecInstr getSpecInstrByInstrIdAndSpecId(Long instrId, Long specId) throws Exception;
}
