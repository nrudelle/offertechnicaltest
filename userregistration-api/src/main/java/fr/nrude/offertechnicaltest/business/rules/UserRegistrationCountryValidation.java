package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserRegistrationCountryValidation implements UserRegistrationValidation{
    public static final Set<String> allowedCountries;
    static {
        allowedCountries = new HashSet<>();
        allowedCountries.add("fr");
    }

    @Override
    public boolean validate(UserRegistrationDTO userRegistrationDTO) {
        return allowedCountries.contains(userRegistrationDTO.countryCode);
    }

    @Override
    public String getValidationFailMessage() {
        return "The only allowed countries are : "+allowedCountries;
    }
}
