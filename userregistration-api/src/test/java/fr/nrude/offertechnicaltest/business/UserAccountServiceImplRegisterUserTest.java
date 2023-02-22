package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessValidationException;
import fr.nrude.offertechnicaltest.business.rules.UserRegistrationImplTestValidation;
import fr.nrude.offertechnicaltest.business.rules.UserRegistrationValidation;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserAccountServiceImplRegisterUserTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Spy
    private ArrayList<UserRegistrationValidation> userRegistrationValidations;

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    @Captor
    ArgumentCaptor<UserAccount> entityCaptor;

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
    void registerUserValidateDtoOk() {
        userRegistrationValidations.add(new UserRegistrationImplTestValidation(true));
        UserRegistrationDTO dto = new UserRegistrationDTO();

        assertDoesNotThrow(() -> {
            userAccountService.registerUser(dto);
        });
    }

    @Test
    void registerUserValidateDtoKo() {
        userRegistrationValidations.add(new UserRegistrationImplTestValidation(true));
        userRegistrationValidations.add(new UserRegistrationImplTestValidation(false));
        UserRegistrationDTO dto = new UserRegistrationDTO();

        assertThrows(BusinessValidationException.class, () -> {
            userAccountService.registerUser(dto);
        });
    }

    @Test
    void registerUserSaveToRepo() throws BusinessValidationException {
        UserRegistrationDTO dto = new UserRegistrationDTO();

        userAccountService.registerUser(dto);

        Mockito.verify(userAccountRepository, Mockito.times(1)).save(entityCaptor.capture());
    }
}