package fr.nrude.offertechnicaltest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequestDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UserRegistrationSpringBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class UserAccountRegisterUserIntegrationTest {

    private static final Date DEFAULT_DATE = new Date();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        userAccountRepository.deleteAll();
    }

    @Test
    void testRegisterUserOkUserCreation() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond", "15-03-1988", "fr", "M", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id", Matchers.not(Matchers.emptyOrNullString())))
                .andExpect(jsonPath("$.result.id", Matchers.greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.result.userName", Matchers.is(requestProvided.userName)))
                .andExpect(jsonPath("$.result.birthDate", Matchers.is(requestProvided.birthDate)))
                .andExpect(jsonPath("$.result.countryCode", Matchers.is(requestProvided.countryCode)))
                .andExpect(jsonPath("$.result.phoneNumber", Matchers.is(requestProvided.phoneNumber)))
                .andExpect(jsonPath("$.result.gender", Matchers.is(requestProvided.gender)));

        List<UserAccount> userCreatedListActual = userAccountRepository.findAll();
        assertFalse(userCreatedListActual.isEmpty());
        assertEquals(1, userCreatedListActual.size());
        UserAccount userCreatedActual = userCreatedListActual.get(0);
        UserAccount userCreatedExpected = createUserAccountEntity(userCreatedActual.getId(),"ldupond", "15-03-1988", "fr", "M", "0255668899");
        assertEquals(userCreatedExpected, userCreatedActual);
    }

    @Test
    void testRegisterUserKoRequestValidationFailMissingMandatoryFields() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest(null,
                null, null, "M", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(3)));
    }

    @Test
    void testRegisterUserOkRequestValidationFailMissingOptionalFields() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1988", "fr", null, null);

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testRegisterUserKoRequestValidationFailUsernameTooLong() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest(
                "unNomTresTresTresTresTresTresTresTresTresTresTresTresLong",
                "15-03-1988", "fr", "M", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoRequestValidationFailBirthDateInvalidPattern() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15/03/1988", "fr", "M", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoRequestValidationFailCountryCodeInvalidPattern() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1988", "france", "M", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoRequestValidationFailPhoneNumberInvalidPattern() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1988", "fr", "M", "02 55 66 88 99");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoRequestValidationFailGenderInvalidPattern() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1988", "fr", "Masculin", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoBusinessValidationFailUsernameNotUnique() throws Exception {
        UserAccount testUser = createUserAccountEntity(1L,"ldupond", "15-03-1988", "fr", "M", "0255668899");;
        userAccountRepository.save(testUser);

        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1987", "fr", "M", "0623568899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoBusinessValidationFailNotAdult() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-2010", "fr", "M", "0623568899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    @Test
    void testRegisterUserKoBusinessValidationFailUnauthorizedCountry() throws Exception {
        UserRegistrationRequestDTO requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1987", "en", "M", "0623568899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.empty())))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)));
    }

    private UserAccount createUserAccountEntity(long id, String userName, String birthDate, String countryCode,
                                                String gender, String phoneNumber) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        userAccount.setUserName(userName);

        LocalDate birthLocalDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        userAccount.setBirthDate(Date.from(birthLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        userAccount.setCountryCode(countryCode);
        userAccount.setPhoneNumber(phoneNumber);
        userAccount.setGender(gender);
        return userAccount;
    }

    private UserRegistrationRequestDTO createUserRegistrationRequest(String userName, String birthDate, String countryCode,
                                                                     String gender, String phoneNumber) {
        UserRegistrationRequestDTO request = new UserRegistrationRequestDTO();
        request.userName = userName;
        request.birthDate = birthDate;
        request.countryCode = countryCode;
        request.gender = gender;
        request.phoneNumber = phoneNumber;

        return request;
    }
}