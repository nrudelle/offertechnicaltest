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
        UserAccount entity = convertToEntity(dto);
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
    private UserAccount convertToEntity(UserRegistrationDTO dto) {
        UserAccount entity = new UserAccount();

        entity.setUserName(dto.userName);
        entity.setBirthDate(dto.birthDate);
        entity.setCountryCode(dto.countryCode);
        entity.setGender(dto.gender);
        entity.setPhoneNumber(dto.phoneNumber);

        return entity;
    }

    @Override
    public UserDetailsDTO getUserDetails(Long id) throws BusinessException {
        Optional<UserAccount> entity = userAccountRepository.findById(id);
        return convertToDTO(entity.orElseThrow(() -> new BusinessException("Pas d'utilisateur avec l'id '"+id+"'")));
    }
    private UserDetailsDTO convertToDTO(UserAccount entity) {
        UserDetailsDTO dto = new UserDetailsDTO();

        dto.userName = entity.getUserName();
        dto.birthDate = entity.getBirthDate();
        dto.countryCode =  entity.getCountryCode();
        dto.gender = entity.getGender();
        dto.phoneNumber = entity.getPhoneNumber();

        return dto;
    }
}
