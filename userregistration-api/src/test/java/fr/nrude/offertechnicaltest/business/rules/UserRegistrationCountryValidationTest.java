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
    void testValidateOk() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.countryCode = "fr";

        assertTrue(validationClass.validate(provided));
    }

    @Test
    void testValidateKo() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.countryCode = "en";

        assertFalse(validationClass.validate(provided));
    }

    @Test
    void testValidateKoNoCountry() {
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.countryCode = null;

        assertThrows(NullPointerException.class, () ->{
            validationClass.validate(provided);
        });
    }
    @Test
    void testValidateKoPassedNull() {
        UserRegistrationDTO provided = null;

        assertThrows(NullPointerException.class, () ->{
            validationClass.validate(provided);
        });
    }

    @Test
    void testGetValidationFailMessage() {
        String validationFailMessage = validationClass.getValidationFailMessage();
        assertNotNull(validationFailMessage);
        assertFalse(validationFailMessage.isEmpty());
    }
}