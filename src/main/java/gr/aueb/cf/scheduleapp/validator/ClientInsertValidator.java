package gr.aueb.cf.scheduleapp.validator;

import gr.aueb.cf.scheduleapp.dto.ClientInsertDTO;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ClientInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return ClientInsertDTO.class == clazz; }

    @Override
    public void validate(Object target, Errors errors) {
        ClientInsertDTO clientInsertDTO = (ClientInsertDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (clientInsertDTO.getFirstname().length() < 2 || clientInsertDTO.getFirstname().length() > 45) {
            errors.rejectValue("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (clientInsertDTO.getLastname().length() < 2 || clientInsertDTO.getLastname().length() > 45) {
            errors.rejectValue("lastname", "size");
        }

        ValidationUtils.rejectIfEmpty(errors, "birthday", "empty");
    }
}
