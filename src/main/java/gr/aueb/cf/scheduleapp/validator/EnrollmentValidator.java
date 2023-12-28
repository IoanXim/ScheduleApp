package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.EnrollmentDTO;
import gr.aueb.cf.scheduleapp.model.Enrollment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EnrollmentValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) { return EnrollmentDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        EnrollmentDTO enrollmentDTO = (EnrollmentDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "client_id", "empty");
        ValidationUtils.rejectIfEmpty(errors, "room_id", "empty");
        ValidationUtils.rejectIfEmpty(errors, "day_id", "empty");
        ValidationUtils.rejectIfEmpty(errors, "starting_time", "empty");
        ValidationUtils.rejectIfEmpty(errors, "ending_time", "empty");
    }
}
