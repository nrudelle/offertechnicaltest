package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.UserAccountService;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;


class UserAccountControllerRegisterUserTest {

    private static final String DEFAULT_DATE = "22-02-2005";

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserAccountController controller;

    private AutoCloseable closable;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(closable != null) {
            closable.close();
        }
    }

    /*
    @Test
    void testRegisterUserNoExceptions() throws BusinessException {
        UserRegistrationRequest provided = getDefaultUserRegistrationRequest();
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        assertDoesNotThrow(() -> {
            ResponseEntity<RequestResult<UserDetailsDTO>> responseActual = controller.registerUser(provided, bindingResult);

            assertNotNull(responseActual);
            assertTrue(responseActual.getStatusCode().is2xxSuccessful());
            assertNotNull(responseActual.getBody());
            assertEquals(true, responseActual.getBody().result);
        });
    }

    @Test
    void testRegisterUserRequestException() throws BusinessException, BusinessValidationException {
        UserRegistrationRequest provided = getDefaultUserRegistrationRequest();
        UserDetailsDTO expected = getDefaultUserDetailsDTO();
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        //Mockito.when(userAccountService.registerUser(any(UserRegistrationDTO.class))).thenThrow(IllegalArgumentException.class);

        assertDoesNotThrow(() -> {
            ResponseEntity<RequestResult<UserDetailsDTO>> responseActual = controller.registerUser(provided, bindingResult);

            assertNotNull(responseActual);
            assertTrue(responseActual.getStatusCode().is2xxSuccessful());
            assertNotNull(responseActual.getBody());
            assertEquals(expected, responseActual.getBody().result);
        });
    }
    */

    private UserRegistrationRequest getDefaultUserRegistrationRequest() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.userName = "ldupond";
        request.birthDate = DEFAULT_DATE;
        request.countryCode = "fr";
        request.gender = "M";
        request.phoneNumber = "0788556622";

        return request;
    }

    /*
    private UserRegistrationDTO getDefaultUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.userName = "ldupond";
        dto.birthDate = DEFAULT_DATE;
        dto.countryCode = "fr";
        dto.gender = "M";
        dto.phoneNumber = "0788556622";

        return dto;
    }
    */

    /*
    private UserDetailsDTO getDefaultUserDetailsDTO() {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.userName = "ldupond";
        dto.birthDate = DEFAULT_DATE;
        dto.countryCode =  "fr";
        dto.gender = "M";
        dto.phoneNumber = "0788556622";

        return dto;
    }
    */
}