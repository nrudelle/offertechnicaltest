package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessValidationException;
import fr.nrude.offertechnicaltest.business.rules.UserRegistrationValidation;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private List<UserRegistrationValidation> userRegistrationValidations;

    @Override
    public UserDetailsDTO registerUser(UserRegistrationDTO dto) throws BusinessValidationException {
        validateUserRegistration(dto);

        UserAccount entity = ConverterUtils.convertToEntity(dto);
        entity = userAccountRepository.save(entity);

        UserDetailsDTO userDetailsDTO = ConverterUtils.convertToDTO(entity);
        return userDetailsDTO;
    }
    private void validateUserRegistration(UserRegistrationDTO dto) throws BusinessValidationException {
        List<String> validationErrors = userRegistrationValidations.stream()
                .filter(validationRule -> !validationRule.validate(dto))
                .map(UserRegistrationValidation::getValidationFailMessage)
                .toList();
        if(!validationErrors.isEmpty()) {
            throw new BusinessValidationException("User registration validation has returned errors.", validationErrors);
        }
    }

    @Override
    public UserDetailsDTO getUserDetails(long id) throws ResourceNotFoundBusinessException {
        Optional<UserAccount> entity = userAccountRepository.findById(id);

        UserDetailsDTO userDetailsDTO = ConverterUtils.convertToDTO(
                entity.orElseThrow(() -> new ResourceNotFoundBusinessException("Pas d'utilisateur avec l'id '"+id+"'")));
        return userDetailsDTO;
    }
}