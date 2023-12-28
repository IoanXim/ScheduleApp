package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.SessionDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SessionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return SessionDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        SessionDTO sessionDTO = (SessionDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "roomId", "empty");
        ValidationUtils.rejectIfEmpty(errors, "dayId", "empty");
        ValidationUtils.rejectIfEmpty(errors, "instrId", "empty");
        ValidationUtils.rejectIfEmpty(errors, "specId", "empty");
        ValidationUtils.rejectIfEmpty(errors, "startingTime", "empty");
        ValidationUtils.rejectIfEmpty(errors, "endingTime", "empty");
    }
}
