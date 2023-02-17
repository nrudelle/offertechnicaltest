package fr.nrude.offertechnicaltest.dao.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UserAccount {
    @Id
    @GeneratedValue
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
}
