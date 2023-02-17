package fr.nrude.offertechnicaltest.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserRegistrationRequest {

    @NotEmpty(message = "Le nom d'utilisateur ne peut pas être vide")
    public String userName;
    @NotEmpty(message = "La date de naissance ne peut pas être vide")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "Le format de la date de naissance est invalide. Format attendu : dd-MM-yyyy")
    public String birthDate;
    @NotEmpty(message = "Le pays ne peut pas être vide")
    public String countryCode;
    public String phoneNumber = "";
    public String gender = "";
}
