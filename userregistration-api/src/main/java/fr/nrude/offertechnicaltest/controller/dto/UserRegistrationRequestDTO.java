package fr.nrude.offertechnicaltest.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationRequestDTO {

    @NotEmpty(message = "Username cannot be empty")
    @Size(max = 50, message = "Username must have a maximum length of 50 characters")
    public String userName;
    @NotEmpty(message = "Birthdate cannot be empty")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Invalid format of birthdate. Expected format : dd-MM-yyyy")
    public String birthDate;
    @NotEmpty(message = "Country code cannot be empty")
    @Pattern(regexp = "^[a-z]{1,2}$", message = "Country code must be composed of 1 or 2 lowercase letters")
    public String countryCode;
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be composed of exactly 10 digits without spaces")
    public String phoneNumber = "";
    @Pattern(regexp = "^M|F$", message = "Invalid format of gender. Possible values : M or F")
    public String gender = "";
}
