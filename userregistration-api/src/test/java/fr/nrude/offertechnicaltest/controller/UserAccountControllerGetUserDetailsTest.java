package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.UserAccountService;
import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.controller.dto.RequestResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountControllerGetUserDetailsTest {

    private static final String DEFAULT_DATE = "22-02-2005";

    @Mock
    private UserAccountService userAccountService;

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

    @Test
    void testGetUserDetailsOkUserFound() throws ResourceNotFoundBusinessException {
        long userIdProvided = 200L;
        UserDetailsDTO expected = getDefaultUserDetailsDTO();
        Mockito.when(userAccountService.getUserDetails(userIdProvided)).thenReturn(expected);

        ResponseEntity<RequestResult<UserDetailsDTO>> responseActual = controller.getUserDetails(userIdProvided);

        assertNotNull(responseActual);
        assertTrue(responseActual.getStatusCode().is2xxSuccessful());
        assertNotNull(responseActual.getBody());
        assertEquals(expected, responseActual.getBody().result);
    }

    @Test
    void testGetUserDetailsOkUserNotFound() throws ResourceNotFoundBusinessException {
        long userIdProvided = 404L;
        Mockito.when(userAccountService.getUserDetails(userIdProvided)).thenThrow(ResourceNotFoundBusinessException.class);

        ResponseEntity<RequestResult<UserDetailsDTO>> responseActual = controller.getUserDetails(userIdProvided);

        assertNotNull(responseActual);
        assertTrue(responseActual.getStatusCode().is5xxServerError());
        assertNotNull(responseActual.getBody());
        RequestResult<UserDetailsDTO> body = (RequestResult<UserDetailsDTO>) responseActual.getBody();
        assertNull(body.result);
        assertNotNull(body.errors);
        assertEquals(1, body.errors.size());
    }

    private UserDetailsDTO getDefaultUserDetailsDTO() {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.userName = "username";
        dto.birthDate = DEFAULT_DATE;
        dto.countryCode =  "fr";
        dto.gender = "F";
        dto.phoneNumber = "0456897721";

        return dto;
    }
}