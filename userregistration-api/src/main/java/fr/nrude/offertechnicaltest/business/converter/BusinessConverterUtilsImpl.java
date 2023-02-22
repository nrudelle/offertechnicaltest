package fr.nrude.offertechnicaltest.business.converter;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class BusinessConverterUtilsImpl implements BusinessConverterUtils {
    public UserAccount convertToEntity(UserRegistrationDTO dto) {
        if(dto == null) {
            return null;
        }

        UserAccount entity = new UserAccount();
        entity.setUserName(dto.userName);
        entity.setBirthDate(dto.birthDate);
        entity.setCountryCode(dto.countryCode);
        entity.setGender(dto.gender);
        entity.setPhoneNumber(dto.phoneNumber);
        return entity;
    }

    public UserDetailsDTO convertToDTO(UserAccount entity) {
        if(entity == null) {
            return null;
        }

        UserDetailsDTO dto = new UserDetailsDTO();
        dto.id = entity.getId();
        dto.userName = entity.getUserName();

        LocalDate birthLocalDate = LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault());
        dto.birthDate = String.format("%2s-%2s-%s",
                birthLocalDate.getDayOfMonth(), birthLocalDate.getMonthValue(), birthLocalDate.getYear())
                .replace(' ', '0');;
        dto.countryCode =  entity.getCountryCode();
        dto.gender = entity.getGender();
        dto.phoneNumber = entity.getPhoneNumber();
        return dto;
    }
}
