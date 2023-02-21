package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessException;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessValidationException;

public interface UserAccountService {
    public UserDetailsDTO registerUser(UserRegistrationDTO dto) throws BusinessValidationException;

    public UserDetailsDTO getUserDetails(long id) throws BusinessException;
}
