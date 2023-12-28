package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.UserUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return UserUpdateDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateDTO userUpdateDTO = (UserUpdateDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (userUpdateDTO.getUsername().length() < 2 || userUpdateDTO.getUsername().length() > 45) {
            errors.rejectValue("username", "size");
        }
        ValidationUtils.rejectIfEmpty(errors, "password", "empty");
    }
}
