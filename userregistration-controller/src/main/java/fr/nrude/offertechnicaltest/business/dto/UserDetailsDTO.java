package fr.nrude.offertechnicaltest.business.dto;

import java.util.Date;
import java.util.Objects;

public class UserDetailsDTO {
    public long id = 0L;
    public String userName = "";
    public Date birthDate;
    public String countryCode = "";
    public String phoneNumber = "";
    public String gender = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDetailsDTO that = (UserDetailsDTO) o;
        return id == that.id
                && Objects.equals(userName, that.userName)
                && Objects.equals(birthDate, that.birthDate)
                && Objects.equals(countryCode, that.countryCode)
                && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(gender, that.gender);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, userName, birthDate, countryCode, phoneNumber, gender);
    }
}
