package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.SpecialtyUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SpecialtyUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return SpecialtyUpdateDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        SpecialtyUpdateDTO specialtyUpdateDTO = (SpecialtyUpdateDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "name", "empty");
        if (specialtyUpdateDTO.getName().length() < 2 || specialtyUpdateDTO.getName().length() > 30) {
            errors.rejectValue("name", "size");
        }
    }
}
