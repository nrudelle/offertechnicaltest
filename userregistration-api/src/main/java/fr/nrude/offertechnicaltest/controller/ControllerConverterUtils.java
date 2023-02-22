package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;

public interface ControllerConverterUtils {
    UserRegistrationDTO convertToRegistrationDTO(UserRegistrationRequest request);
}
