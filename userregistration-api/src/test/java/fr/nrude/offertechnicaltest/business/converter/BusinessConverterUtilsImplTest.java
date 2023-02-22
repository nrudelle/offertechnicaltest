package fr.nrude.offertechnicaltest.business.converter;

import fr.nrude.offertechnicaltest.business.converter.BusinessConverterUtils;
import fr.nrude.offertechnicaltest.business.converter.BusinessConverterUtilsImpl;
import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessConverterUtilsImplTest {

    private static final Date DEFAULT_DATE = new Date();

    private static final BusinessConverterUtils CONVERTER = new BusinessConverterUtilsImpl();

    @Test
    void testConvertUserRegistrationDTOtoUserAccountEntity() {
        UserRegistrationDTO provided = getDefaultUserRegistrationDTO();
        UserAccount expected = getDefaultUserAccountEntity();

        UserAccount actual = CONVERTER.convertToEntity(provided);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertUserAccountEntitytoUserDetailsDTO() {
        UserAccount provided = getDefaultUserAccountEntity();
        UserDetailsDTO expected = getDefaultUserDetailsDTO();

        UserDetailsDTO actual = CONVERTER.convertToDTO(provided);

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
        LocalDate birthLocalDate = LocalDate.ofInstant(DEFAULT_DATE.toInstant(), ZoneId.systemDefault());
        dto.birthDate = String.format("%2s-%2s-%s",
                birthLocalDate.getDayOfMonth(), birthLocalDate.getMonthValue(), birthLocalDate.getYear())
                .replace(' ', '0');
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