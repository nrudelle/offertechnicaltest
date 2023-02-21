package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Component
public class UserRegistrationAdultValidation implements UserRegistrationValidation{
    public static final int MINIMUM_AGE_ADULT = 18;

    @Override
    public boolean validate(UserRegistrationDTO userRegistrationDTO) {
        int actualAge = getActualAge(userRegistrationDTO.birthDate);
        System.out.println("actualAge : "+actualAge);
        return actualAge >= MINIMUM_AGE_ADULT;
    }
    private int getActualAge(Date birthDate) {
        LocalDate localBirthDate = Instant.ofEpochMilli(birthDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate now = LocalDate.now();
        return Period.between(localBirthDate, now).getYears();
    }

    @Override
    public String getValidationFailMessage() {
        return "Only adults can create an account";
    }
}
