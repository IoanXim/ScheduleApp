package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.RoomDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoomValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) { return RoomDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        RoomDTO roomDTO = (RoomDTO) target;

        ValidationUtils.rejectIfEmpty(errors, "id", "empty");
    }
}
