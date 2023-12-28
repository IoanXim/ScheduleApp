package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.SpecialtyInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SpecialtyInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return SpecialtyInsertDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        SpecialtyInsertDTO specialtyInsertDTO = (SpecialtyInsertDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "name", "empty");
        if (specialtyInsertDTO.getName().length() < 2 || specialtyInsertDTO.getName().length() > 30) {
            errors.rejectValue("name", "size");
        }
    }
}
