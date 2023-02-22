package fr.nrude.offertechnicaltest;

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

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UsersSpringBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class UserAccountGetUserDetailsIntegrationTest {

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
    void testGetUserDetailsUserFound() throws Exception {
        UserAccount testUser = getTestUser();
        testUser = userAccountRepository.save(testUser);

        Long idProvided = testUser.getId();

        mvc.perform(MockMvcRequestBuilders.get("/users/"+idProvided+"/getDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id", Matchers.is(idProvided.intValue())))
                .andExpect(jsonPath("$.result.userName", Matchers.is(testUser.getUserName())))
                .andExpect(jsonPath("$.result.countryCode", Matchers.is(testUser.getCountryCode())))
                .andExpect(jsonPath("$.result.phoneNumber", Matchers.is(testUser.getPhoneNumber())))
                .andExpect(jsonPath("$.result.gender", Matchers.is(testUser.getGender())));
    }

    @Test
    void testGetUserDetailsUserNotFound() throws Exception {
        long idProvided = 1L;

        mvc.perform(MockMvcRequestBuilders.get("/users/"+idProvided+"/getDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", Matchers.nullValue()))
                .andExpect(jsonPath("$.errors", Matchers.not(Matchers.emptyOrNullString())));
    }

    private UserAccount getTestUser() {
        UserAccount testUser = new UserAccount();
        testUser.setUserName("ldupond");
        testUser.setBirthDate(DEFAULT_DATE);
        testUser.setCountryCode("fr");
        testUser.setPhoneNumber("0698286200");
        testUser.setGender("M");
        return testUser;
    }
}