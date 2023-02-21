package fr.nrude.offertechnicaltest.dao.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id = 0L;

    @Column(length = 50, nullable = false, unique = true)
    private String userName = "";

    @Column(nullable = false)
    private Date birthDate = null;

    @Column(length = 2, nullable = false)
    private String countryCode = "";

    @Column(length = 10)
    private String phoneNumber = "";

    @Column(length = 1)
    private String gender = "";

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAccount that = (UserAccount) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getUserName(), that.getUserName())
                && Objects.equals(getBirthDate(), that.getBirthDate())
                && Objects.equals(getCountryCode(), that.getCountryCode())
                && Objects.equals(getPhoneNumber(), that.getPhoneNumber())
                && Objects.equals(getGender(), that.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getBirthDate(), getCountryCode(), getPhoneNumber(), getGender());
    }
}
