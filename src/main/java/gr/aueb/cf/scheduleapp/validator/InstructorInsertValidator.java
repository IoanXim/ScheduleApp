package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.InstructorInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InstructorInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return InstructorInsertDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        InstructorInsertDTO instructorInsertDTO = (InstructorInsertDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (instructorInsertDTO.getFirstname().length() < 2 || instructorInsertDTO.getFirstname().length() > 30) {
            errors.rejectValue("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (instructorInsertDTO.getLastname().length() < 2 || instructorInsertDTO.getLastname().length() > 30) {
            errors.rejectValue("lastname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "empty");
        if (instructorInsertDTO.getSsn().toString().length() != 9) {
            errors.rejectValue("ssn", "size");
        }
    }
}
