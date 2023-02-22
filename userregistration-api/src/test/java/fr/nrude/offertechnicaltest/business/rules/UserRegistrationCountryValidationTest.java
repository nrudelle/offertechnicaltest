package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationCountryValidationTest {

    private UserRegistrationCountryValidation validationClass;

    @BeforeEach
    void setup() {
        validationClass = new UserRegistrationCountryValidation();
    }

    @AfterEach
    void tearDown() {
        validationClass = null;
    }

    @Test
    void validateOk() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.countryCode = "fr";

        assertTrue(validationClass.validate(provided));
    }

    @Test
    void validateKo() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.countryCode = "en";

        assertFalse(validationClass.validate(provided));
    }

    @Test
    void getValidationFailMessage() {
        String validationFailMessage = validationClass.getValidationFailMessage();
        assertNotNull(validationFailMessage);
        assertFalse(validationFailMessage.isEmpty());
    }
}