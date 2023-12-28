package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.SpecInstrDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SpecInstrValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return SpecInstrDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        SpecInstrDTO specInstrDTO = (SpecInstrDTO) target;

        if (specInstrDTO.getInstrId() == null || specInstrDTO.getSpecId() == null) {
            errors.rejectValue("id", "empty");
        }
    }
}
