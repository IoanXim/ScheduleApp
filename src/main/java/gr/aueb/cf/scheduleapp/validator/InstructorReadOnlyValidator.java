package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.InstructorReadOnlyDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InstructorReadOnlyValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return InstructorReadOnlyDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        InstructorReadOnlyDTO instructorReadOnlyDTO = (InstructorReadOnlyDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (instructorReadOnlyDTO.getFirstname().length() < 2 || instructorReadOnlyDTO.getFirstname().length() > 30) {
            errors.rejectValue("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (instructorReadOnlyDTO.getLastname().length() < 2 || instructorReadOnlyDTO.getLastname().length() > 30) {
            errors.rejectValue("lastname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "empty");
        if (instructorReadOnlyDTO.getSsn().toString().length() != 9) {
            errors.rejectValue("ssn", "size");
        }
    }
}
