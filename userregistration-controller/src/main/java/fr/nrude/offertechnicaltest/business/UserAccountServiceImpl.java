package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessException;
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
    public void registerUser(UserRegistrationDTO dto) throws BusinessValidationException {
        validateUserRegistration(dto);
        UserAccount entity = ConverterUtils.convertToEntity(dto);
        userAccountRepository.save(entity);
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
    public UserDetailsDTO getUserDetails(long id) throws BusinessException {
        Optional<UserAccount> entity = userAccountRepository.findById(id);
        return ConverterUtils.convertToDTO(entity.orElseThrow(() -> new BusinessException("Pas d'utilisateur avec l'id '"+id+"'")));
    }
}
