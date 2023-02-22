package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConverterUtils {
    public static UserRegistrationDTO convertToRegistrationDTO(UserRegistrationRequest request) {
        UserRegistrationDTO dto = new UserRegistrationDTO();

        try {

            dto.userName = request.userName;
            LocalDate birthDate = LocalDate.parse(request.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            dto.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dto.countryCode = request.countryCode;
            dto.gender = request.gender;
            dto.phoneNumber = request.phoneNumber;

        } catch(DateTimeException e) {
            throw new IllegalArgumentException("Invalid date : "+e.getMessage());
        }

        return dto;
    }
}
