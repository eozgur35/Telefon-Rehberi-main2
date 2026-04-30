package duzce.bm.mf.telefonrehberi.controller;

import duzce.bm.mf.telefonrehberi.exception.WebserviceValidationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BaseController {
    protected void validate(Object object, String objectName, Validator validator)
    {
        Errors errors = new BeanPropertyBindingResult(object, objectName);
        validator.validate(object, errors);
        if (errors.hasErrors())
        {
            throw new WebserviceValidationException(errors);
        }
    }
}
