package fr.nrude.offertechnicaltest.controller.converter;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ControllerConverterUtilsImplTest {

    private static final ControllerConverterUtils CONVERTER = new ControllerConverterUtilsImpl();

    private static final Date DEFAULT_DATE = java.util.Date.from(LocalDate.of(2023,2,20)
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());

    @Test
    void testConvertUserRegistrationRequestToUserRegistrationDTO() {
        UserRegistrationRequestDTO provided = getUserRegistrationRequest("ldupond", "fr", DEFAULT_DATE,
                "M", "0788556622");
        UserRegistrationDTO expected = getUserRegistrationDTO("ldupond", "fr", DEFAULT_DATE,
                "M", "0788556622");

        UserRegistrationDTO actual = CONVERTER.convertToRegistrationDTO(provided);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertUserRegistrationRequestToUserRegistrationDTOwithoutBirthDate() {
        UserRegistrationRequestDTO provided = getUserRegistrationRequest("ldupond", "fr", null,
                "M", "0788556622");
        UserRegistrationDTO expected = getUserRegistrationDTO("ldupond", "fr", null,
                "M", "0788556622");

        UserRegistrationDTO actual = CONVERTER.convertToRegistrationDTO(provided);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertUserRegistrationRequestToUserRegistrationDTOnullPassed() {
        UserRegistrationRequestDTO provided = null;

        UserRegistrationDTO actual = CONVERTER.convertToRegistrationDTO(provided);

        assertNull(actual);
    }

    private UserRegistrationRequestDTO getUserRegistrationRequest(String userName, String countryCode, Date birthDate,
                                       String gender, String phoneNumber) {
        UserRegistrationRequestDTO request = new UserRegistrationRequestDTO();
        request.userName = userName;
        request.countryCode = countryCode;
        if(birthDate != null) {
            LocalDate localDefaultDate = DEFAULT_DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            request.birthDate = String.format("%2s-%2s-%s",
                    localDefaultDate.getDayOfMonth(),
                    localDefaultDate.getMonthValue(),
                    localDefaultDate.getYear()).replace(' ', '0');
        }
        request.gender = gender;
        request.phoneNumber = phoneNumber;
        return request;
    }

    private UserRegistrationDTO getUserRegistrationDTO(String userName, String countryCode, Date birthDate,
                                                       String gender, String phoneNumber) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = userName;
        dto.birthDate = birthDate;
        dto.countryCode = countryCode;
        dto.gender = gender;
        dto.phoneNumber = phoneNumber;

        return dto;
    }

    private UserRegistrationDTO getDefaultUserRegistrationDTO(Date birthDate) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = "ldupond";
        dto.birthDate = birthDate;
        dto.countryCode = "fr";
        dto.gender = "M";
        dto.phoneNumber = "0788556622";

        return dto;
    }


}