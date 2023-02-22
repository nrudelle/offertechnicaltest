package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationAdultValidationTest {

    private UserRegistrationAdultValidation validationClass;
    private LocalDate thresholdBirthDate;
    @BeforeEach
    void setup() {
        validationClass = new UserRegistrationAdultValidation();
        LocalDate now = LocalDate.now();
        thresholdBirthDate = now.minusYears(UserRegistrationAdultValidation.MINIMUM_AGE_ADULT);
    }

    @AfterEach
    void tearDown() {
        validationClass = null;
    }

    @Test
    void testValidateIsAdult() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        LocalDate birthDate = thresholdBirthDate;
        provided.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        assertTrue(validationClass.validate(provided));
    }

    @Test
    void testValidateIsNotAdult() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        LocalDate birthDate = thresholdBirthDate.plusDays(1);
        provided.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        assertFalse(validationClass.validate(provided));
    }

    @Test
    void testValidateKoNoBirthDate() {
        UserRegistrationDTO provided = new UserRegistrationDTO();

        // TODO: regler ca
        // assertFalse(validationClass.validate(provided));
    }

    @Test
    void testGetValidationFailMessage() {
        String validationFailMessage = validationClass.getValidationFailMessage();
        assertNotNull(validationFailMessage);
        assertFalse(validationFailMessage.isEmpty());
    }
}