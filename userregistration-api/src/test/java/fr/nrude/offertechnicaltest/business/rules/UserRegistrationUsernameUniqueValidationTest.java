package fr.nrude.offertechnicaltest.business.rules;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRegistrationUsernameUniqueValidationTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserRegistrationUsernameUniqueValidation validationClass;

    private AutoCloseable closable;

    @BeforeEach
    void setup() {
        closable = MockitoAnnotations.openMocks(this);
        validationClass = new UserRegistrationUsernameUniqueValidation(userAccountRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        if(closable != null) {
            closable.close();
        }
    }

    @Test
    void validateOkUsernameIsUnique() {
        String userName = "cmorgan";
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.userName = userName;

        Mockito.when(userAccountRepository.getByUsername(userName)).thenReturn(Optional.empty());

        assertTrue(validationClass.validate(provided));
        Mockito.verify(userAccountRepository, Mockito.times(1)).getByUsername(userName);
    }
    @Test
    void validateOkUsernameIsNotUnique() {
        String userName = "ldupond";
        UserRegistrationDTO provided = new UserRegistrationDTO();
        provided.userName = userName;

        UserAccount userFound = new UserAccount();
        Mockito.when(userAccountRepository.getByUsername(userName)).thenReturn(Optional.of(userFound));

        assertFalse(validationClass.validate(provided));
        Mockito.verify(userAccountRepository, Mockito.times(1)).getByUsername(userName);
    }

    @Test
    void getValidationFailMessage() {
        String validationFailMessage = validationClass.getValidationFailMessage();
        assertNotNull(validationFailMessage);
        assertFalse(validationFailMessage.isEmpty());
    }
}