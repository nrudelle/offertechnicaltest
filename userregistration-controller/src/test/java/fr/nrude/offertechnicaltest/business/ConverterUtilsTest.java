package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterUtilsTest {

    private static final Date DEFAULT_DATE = new Date();

    @Test
    void convertUserRegistrationDTOtoUserAccountEntity() {
        UserRegistrationDTO provided = getDefaultUserRegistrationDTO();
        UserAccount expected = getDefaultUserAccountEntity();

        UserAccount actual = ConverterUtils.convertToEntity(provided);

        assertEquals(expected, actual);
    }

    @Test
    void convertUserAccountEntitytoUserDetailsDTO() {
        UserAccount provided = getDefaultUserAccountEntity();
        UserDetailsDTO expected = getDefaultUserDetailsDTO();

        UserDetailsDTO actual = ConverterUtils.convertToDTO(provided);

        assertEquals(expected, actual);
    }

    private UserRegistrationDTO getDefaultUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = "username";
        dto.birthDate = DEFAULT_DATE;
        dto.countryCode =  "fr";
        dto.gender = "F";
        dto.phoneNumber = "0456897721";

        return dto;
    }

    private UserDetailsDTO getDefaultUserDetailsDTO() {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.userName = "username";
        dto.birthDate = DEFAULT_DATE;
        dto.countryCode =  "fr";
        dto.gender = "F";
        dto.phoneNumber = "0456897721";

        return dto;
    }

    private UserAccount getDefaultUserAccountEntity() {
        UserAccount entity = new UserAccount();
        entity.setUserName("username");
        entity.setBirthDate(DEFAULT_DATE);
        entity.setCountryCode("fr");
        entity.setGender("F");
        entity.setPhoneNumber("0456897721");

        return entity;
    }
}