package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.UserInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return UserInsertDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        UserInsertDTO userInsertDTO = (UserInsertDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (userInsertDTO.getUsername().length() < 2 || userInsertDTO.getUsername().length() > 45) {
            errors.rejectValue("username", "size");
        }
        ValidationUtils.rejectIfEmpty(errors, "password", "empty");
    }
}
