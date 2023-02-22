package fr.nrude.offertechnicaltest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UsersSpringBootApplication.class)
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
    }

    @Test
    void testRegisterUserOk() throws Exception {
        UserRegistrationRequest requestProvided = createUserRegistrationRequest("ldupond", "15-03-1988", "fr", "M", "0255668899");

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id", Matchers.is(1)))
                .andExpect(jsonPath("$.result.userName", Matchers.is(requestProvided.userName)))
                .andExpect(jsonPath("$.result.birthDate", Matchers.is(requestProvided.birthDate)))
                .andExpect(jsonPath("$.result.countryCode", Matchers.is(requestProvided.countryCode)))
                .andExpect(jsonPath("$.result.phoneNumber", Matchers.is(requestProvided.phoneNumber)))
                .andExpect(jsonPath("$.result.gender", Matchers.is(requestProvided.gender)));

        UserAccount userCreatedExpected = createUserAccount(1L,"ldupond", "15-03-1988", "fr", "M", "0255668899");
        Optional<UserAccount> userCreatedActual = userAccountRepository.findById(1L);
        assertFalse(userCreatedActual.isEmpty());
        assertNotNull(userCreatedActual.get());
        assertEquals(userCreatedExpected, userCreatedActual.get());
    }
    private UserAccount createUserAccount(long id, String userName, String birthDate, String countryCode,
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

    @Test
    void testRegisterUserKoRequestValidationFailUsernameTooLong() throws Exception {
        UserRegistrationRequest requestProvided = createUserRegistrationRequest(
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
        UserRegistrationRequest requestProvided = createUserRegistrationRequest("ldupond",
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
        UserRegistrationRequest requestProvided = createUserRegistrationRequest("ldupond",
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
        UserRegistrationRequest requestProvided = createUserRegistrationRequest("ldupond",
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
        UserRegistrationRequest requestProvided = createUserRegistrationRequest("ldupond",
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
    void testRegisterUserKoRequestValidationFailMissingMandatoryFields() throws Exception {
        UserRegistrationRequest requestProvided = createUserRegistrationRequest(null,
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
        UserRegistrationRequest requestProvided = createUserRegistrationRequest("ldupond",
                "15-03-1988", "fr", null, null);

        mvc.perform(MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestProvided)))
                .andExpect(status().is2xxSuccessful());
    }

    private UserRegistrationRequest createUserRegistrationRequest(String userName, String birthDate, String countryCode,
                                                              String gender, String phoneNumber) {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.userName = userName;
        request.birthDate = birthDate;
        request.countryCode = countryCode;
        request.gender = gender;
        request.phoneNumber = phoneNumber;

        return request;
    }
}