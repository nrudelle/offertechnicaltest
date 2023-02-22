package fr.nrude.offertechnicaltest.business.exceptions;

import java.util.List;

public class BusinessValidationException extends Exception {
    private List<String> validationErrorMessages;
    public BusinessValidationException(String message, List<String> validationErrorMessages) {
        super(message);
        this.validationErrorMessages = validationErrorMessages;
    }
    public List<String> getValidationErrorMessages() {
        return validationErrorMessages;
    }
}
