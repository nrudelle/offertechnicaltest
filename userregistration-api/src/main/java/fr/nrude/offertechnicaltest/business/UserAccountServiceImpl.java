package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.business.exceptions.ValidationBusinessException;
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

    @Autowired
    private BusinessConverterUtils converter;

    @Override
    public UserDetailsDTO registerUser(UserRegistrationDTO dto) throws ValidationBusinessException {
        validateUserRegistration(dto);

        UserAccount entity = converter.convertToEntity(dto);
        entity = userAccountRepository.save(entity);

        return converter.convertToDTO(entity);
    }
    private void validateUserRegistration(UserRegistrationDTO dto) throws ValidationBusinessException {
        List<String> validationErrors = userRegistrationValidations.stream()
                .filter(validationRule -> !validationRule.validate(dto))
                .map(UserRegistrationValidation::getValidationFailMessage)
                .toList();
        if(!validationErrors.isEmpty()) {
            throw new ValidationBusinessException("User registration validation has returned errors.", validationErrors);
        }
    }

    @Override
    public UserDetailsDTO getUserDetails(long id) throws ResourceNotFoundBusinessException {
        Optional<UserAccount> entity = userAccountRepository.findById(id);

        return converter.convertToDTO(
                entity.orElseThrow(() -> new ResourceNotFoundBusinessException("Pas d'utilisateur avec l'id '"+id+"'")));
    }
}
