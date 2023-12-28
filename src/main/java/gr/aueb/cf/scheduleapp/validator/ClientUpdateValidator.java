package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.ClientUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ClientUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return ClientUpdateDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        ClientUpdateDTO clientUpdateDTO = (ClientUpdateDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (clientUpdateDTO.getFirstname().length() < 2 || clientUpdateDTO.getFirstname().length() > 30) {
            errors.rejectValue("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (clientUpdateDTO.getLastname().length() < 2 || clientUpdateDTO.getLastname().length() > 30) {
            errors.rejectValue("lastname", "size");
        }

        ValidationUtils.rejectIfEmpty(errors, "birthday", "empty");
    }
}
