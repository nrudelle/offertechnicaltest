package fr.nrude.offertechnicaltest.controller.converter;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerConverterUtilsImplTest {

    private static final ControllerConverterUtils CONVERTER = new ControllerConverterUtilsImpl();

    private static final Date DEFAULT_DATE = java.util.Date.from(LocalDate.of(2023,2,20)
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());

    @Test
    void testConvertUserRegistrationRequestToUserRegistrationDTO() {
        UserRegistrationRequestDTO provided = getDefaultUserRegistrationRequest();
        UserRegistrationDTO expected = getDefaultUserRegistrationDTO();

        UserRegistrationDTO actual = CONVERTER.convertToRegistrationDTO(provided);

        assertEquals(expected, actual);
    }

    private UserRegistrationRequestDTO getDefaultUserRegistrationRequest() {
        UserRegistrationRequestDTO request = new UserRegistrationRequestDTO();
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