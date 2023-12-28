package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.InstructorUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InstructorUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return InstructorUpdateDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        InstructorUpdateDTO instructorUpdateDTO = (InstructorUpdateDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (instructorUpdateDTO.getFirstname().length() < 2 || instructorUpdateDTO.getFirstname().length() > 30) {
            errors.rejectValue("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (instructorUpdateDTO.getLastname().length() < 2 || instructorUpdateDTO.getLastname().length() > 30) {
            errors.rejectValue("lastname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "empty");
        if (instructorUpdateDTO.getSsn().toString().length() != 9) {
            errors.rejectValue("ssn", "size");
        }
    }
}
