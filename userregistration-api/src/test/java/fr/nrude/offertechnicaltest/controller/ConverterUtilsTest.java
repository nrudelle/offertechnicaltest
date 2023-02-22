package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterUtilsTest {

    private static final Date DEFAULT_DATE = java.util.Date.from(LocalDate.of(2023,2,20)
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());

    @Test
    void testConvertUserRegistrationRequestToUserRegistrationDTO() {
        UserRegistrationRequest provided = getDefaultUserRegistrationRequest();
        UserRegistrationDTO expected = getDefaultUserRegistrationDTO();

        UserRegistrationDTO actual = ConverterUtils.convertToRegistrationDTO(provided);

        assertEquals(expected, actual);
    }

    private UserRegistrationRequest getDefaultUserRegistrationRequest() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.userName = "ldupond";
        LocalDate localDefaultDate = DEFAULT_DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        request.birthDate = String.format("%2s-%2s-%s",
                localDefaultDate.getDayOfMonth(),
                localDefaultDate.getMonthValue(),
                localDefaultDate.getYear()).replace(' ', '0');
        request.countryCode = "fr";
        request.gender = "M";
        request.phoneNumber = "0788556622";

        return request;
    }
    private UserRegistrationDTO getDefaultUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = "ldupond";
        dto.birthDate = DEFAULT_DATE;
        dto.countryCode = "fr";
        dto.gender = "M";
        dto.phoneNumber = "0788556622";

        return dto;
    }


}