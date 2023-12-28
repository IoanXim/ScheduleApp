package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.SpecialtyReadOnlyDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SpecialtyReadOnlyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return SpecialtyReadOnlyDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        SpecialtyReadOnlyDTO specialtyReadOnlyDTO = (SpecialtyReadOnlyDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "name", "empty");
        if (specialtyReadOnlyDTO.getName().length() < 2 || specialtyReadOnlyDTO.getName().length() > 30) {
            errors.rejectValue("name", "size");
        }
    }
}

