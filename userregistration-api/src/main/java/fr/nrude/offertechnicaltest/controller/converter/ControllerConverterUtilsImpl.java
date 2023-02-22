package fr.nrude.offertechnicaltest.controller.converter;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequestDTO;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class ControllerConverterUtilsImpl implements ControllerConverterUtils {
    public UserRegistrationDTO convertToRegistrationDTO(UserRegistrationRequestDTO request) {
        if(request == null) {
            return null;
        }

        try {
            return doConvertToRegistrationDTO(request);
        } catch(DateTimeException e) {
            throw new IllegalArgumentException("Invalid date : "+e.getMessage());
        }
    }
    private UserRegistrationDTO doConvertToRegistrationDTO(UserRegistrationRequestDTO request) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = request.userName;
        if(request.birthDate != null) {
            LocalDate birthDate = LocalDate.parse(request.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            dto.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        dto.countryCode = request.countryCode;
        dto.gender = request.gender;
        dto.phoneNumber = request.phoneNumber;
        return dto;
    }
}
