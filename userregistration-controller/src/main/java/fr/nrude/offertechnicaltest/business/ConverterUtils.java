package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;

public class ConverterUtils {
    public static UserAccount convertToEntity(UserRegistrationDTO dto) {
        UserAccount entity = new UserAccount();

        entity.setUserName(dto.userName);
        entity.setBirthDate(dto.birthDate);
        entity.setCountryCode(dto.countryCode);
        entity.setGender(dto.gender);
        entity.setPhoneNumber(dto.phoneNumber);

        return entity;
    }

    public static UserDetailsDTO convertToDTO(UserAccount entity) {
        UserDetailsDTO dto = new UserDetailsDTO();

        dto.userName = entity.getUserName();
        dto.birthDate = entity.getBirthDate();
        dto.countryCode =  entity.getCountryCode();
        dto.gender = entity.getGender();
        dto.phoneNumber = entity.getPhoneNumber();

        return dto;
    }
}
