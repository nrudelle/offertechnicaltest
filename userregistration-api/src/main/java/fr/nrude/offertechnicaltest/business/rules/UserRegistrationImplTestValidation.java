package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;

public class UserRegistrationImplTestValidation implements UserRegistrationValidation {
    public boolean actualResult;

    public UserRegistrationImplTestValidation(boolean actualResult) {
        this.actualResult = actualResult;
    }

    @Override
    public boolean validate(UserRegistrationDTO userRegistrationDTO) {
        return actualResult;
    }

    @Override
    public String getValidationFailMessage() {
        return "ERROR";
    }
}
