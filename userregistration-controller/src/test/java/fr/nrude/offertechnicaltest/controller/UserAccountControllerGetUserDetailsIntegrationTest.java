package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.UsersSpringBootApplication;
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
class UserAccountControllerGetUserDetailsIntegrationTest {

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
    void getUserDetails() throws Exception {
        long idProvided = 1L;
        UserAccount testUser = getTestUser();
        testUser.setId(idProvided);
        userAccountRepository.save(testUser);

        /*
        actual :
        {"result":{"userName":"ldupond","birthDate":"2023-02-21T14:27:00.659+00:00","countryCode":"fr",
        "phoneNumber":"0698286200","gender":"M"},"errors":[]}
         */

        mvc.perform(MockMvcRequestBuilders.get("/users/"+idProvided+"/getDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.userName", Matchers.is("ldupond")));
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