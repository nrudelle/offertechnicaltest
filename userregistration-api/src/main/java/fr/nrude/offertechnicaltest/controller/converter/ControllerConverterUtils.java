package fr.nrude.offertechnicaltest.controller.converter;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequestDTO;

public interface ControllerConverterUtils {
    UserRegistrationDTO convertToRegistrationDTO(UserRegistrationRequestDTO request);
}
