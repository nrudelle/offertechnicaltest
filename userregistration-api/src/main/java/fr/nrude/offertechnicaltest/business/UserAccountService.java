package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.business.exceptions.ValidationBusinessException;

public interface UserAccountService {
    public UserDetailsDTO registerUser(UserRegistrationDTO dto) throws ValidationBusinessException;

    public UserDetailsDTO getUserDetails(long id) throws ResourceNotFoundBusinessException;
}
