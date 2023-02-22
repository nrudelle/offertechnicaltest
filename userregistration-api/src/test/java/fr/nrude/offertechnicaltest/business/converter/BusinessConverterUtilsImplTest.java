package fr.nrude.offertechnicaltest.business.converter;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BusinessConverterUtilsImplTest {

    private static final Date DEFAULT_DATE = new Date();

    private static final BusinessConverterUtils CONVERTER = new BusinessConverterUtilsImpl();

    @Test
    void testConvertUserRegistrationDTOtoUserAccountEntity() {
        UserRegistrationDTO provided = getDefaultUserRegistrationDTO("ldupond", "fr", DEFAULT_DATE,
                "F", "0456897721");
        UserAccount expected = getDefaultUserAccountEntity("ldupond", "fr", DEFAULT_DATE,
                "F", "0456897721");

        UserAccount actual = CONVERTER.convertToEntity(provided);

        assertEquals(expected, actual);
    }
    @Test
    void testConvertUserRegistrationDTOtoUserAccountEntitypassedNull() {
        UserRegistrationDTO provided = null;

        UserAccount actual = CONVERTER.convertToEntity(provided);

        assertNull(actual);
    }

    @Test
    void testConvertUserAccountEntitytoUserDetailsDTO() {
        UserAccount provided = getDefaultUserAccountEntity("ldupond", "fr",DEFAULT_DATE,
                "F", "0456897721");
        UserDetailsDTO expected = getDefaultUserDetailsDTO("ldupond", "fr",DEFAULT_DATE,
                "F", "0456897721");

        UserDetailsDTO actual = CONVERTER.convertToDTO(provided);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertUserAccountEntitytoUserDetailsDTOwithBirthDateNull() {
        UserAccount provided = getDefaultUserAccountEntity("ldupond", "fr",null,
                "F", "0456897721");
        UserDetailsDTO expected = getDefaultUserDetailsDTO("ldupond", "fr",null,
                "F", "0456897721");

        UserDetailsDTO actual = CONVERTER.convertToDTO(provided);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertUserAccountEntitytoUserDetailsDTOpassedNull() {
        UserAccount provided = null;

        UserDetailsDTO actual = CONVERTER.convertToDTO(provided);

        assertNull(actual);
    }

    private UserRegistrationDTO getDefaultUserRegistrationDTO(String userName, String countryCode, Date birthDate,
                                                              String gender, String phoneNumber) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = userName;
        dto.countryCode =  countryCode;
        dto.birthDate = birthDate;
        dto.gender = gender;
        dto.phoneNumber = phoneNumber;

        return dto;
    }

    private UserDetailsDTO getDefaultUserDetailsDTO(String userName, String countryCode, Date birthDate,
                                                    String gender, String phoneNumber) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.userName = userName;
        dto.countryCode =  countryCode;
        if(birthDate != null) {
            LocalDate birthLocalDate = LocalDate.ofInstant(birthDate.toInstant(), ZoneId.systemDefault());
            dto.birthDate = String.format("%2s-%2s-%s",
                            birthLocalDate.getDayOfMonth(), birthLocalDate.getMonthValue(), birthLocalDate.getYear())
                    .replace(' ', '0');
        }
        dto.gender = gender;
        dto.phoneNumber = phoneNumber;

        return dto;
    }

    private UserAccount getDefaultUserAccountEntity(String userName, String countryCode, Date birthDate,
                                                    String gender, String phoneNumber) {
        UserAccount entity = new UserAccount();
        entity.setUserName(userName);
        entity.setCountryCode(countryCode);
        entity.setBirthDate(birthDate);
        entity.setGender(gender);
        entity.setPhoneNumber(phoneNumber);

        return entity;
    }
}