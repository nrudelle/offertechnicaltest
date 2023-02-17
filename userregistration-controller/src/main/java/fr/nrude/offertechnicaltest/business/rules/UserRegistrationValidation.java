package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;

public interface UserRegistrationValidation {
    public boolean validate(UserRegistrationDTO userRegistrationDTO);
    public String getValidationFailMessage();
}
