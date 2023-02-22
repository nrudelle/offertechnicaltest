package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;

public interface BusinessConverterUtils {
        UserAccount convertToEntity(UserRegistrationDTO dto);
        UserDetailsDTO convertToDTO(UserAccount entity);
}
