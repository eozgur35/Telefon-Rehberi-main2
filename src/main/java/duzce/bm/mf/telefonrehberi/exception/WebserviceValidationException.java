package duzce.bm.mf.telefonrehberi.exception;

import org.springframework.validation.Errors;

public class WebserviceValidationException extends RuntimeException {

    private final Errors errors;

    public WebserviceValidationException(Errors errors) {
        super();
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

}