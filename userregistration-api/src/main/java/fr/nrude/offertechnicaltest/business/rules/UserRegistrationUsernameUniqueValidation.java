package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserRegistrationUsernameUniqueValidation implements UserRegistrationValidation{
    @Autowired
    private final UserAccountRepository userAccountRepository;

    public UserRegistrationUsernameUniqueValidation(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public boolean validate(UserRegistrationDTO userRegistrationDTO) {
        Objects.requireNonNull(userRegistrationDTO);
        Objects.requireNonNull(userRegistrationDTO.userName);

        Optional<UserAccount> userAccountMatch = userAccountRepository.getByUsername(userRegistrationDTO.userName);
        return userAccountMatch.isEmpty();
    }
    @Override
    public String getValidationFailMessage() {
        return "This username is already used";
    }
}
