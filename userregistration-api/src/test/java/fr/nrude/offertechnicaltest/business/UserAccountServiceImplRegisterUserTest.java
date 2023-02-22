package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.converter.BusinessConverterUtils;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ValidationBusinessException;
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

    @Mock
    private BusinessConverterUtils converter;

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
    void testRegisterUserValidateDtoOk() {
        userRegistrationValidations.add(new UserRegistrationImplTestValidation(true));
        UserRegistrationDTO dto = new UserRegistrationDTO();
        Mockito.when(converter.convertToEntity(dto)).thenReturn(new UserAccount());

        assertDoesNotThrow(() -> {
            userAccountService.registerUser(dto);
        });
    }

    @Test
    void testRegisterUserValidateDtoKoPassedNull() {
        assertThrows(NullPointerException.class, () -> {
            userAccountService.registerUser(null);
        });
    }

    @Test
    void testRegisterUserValidateDtoValidationFailure() {
        userRegistrationValidations.add(new UserRegistrationImplTestValidation(true));
        userRegistrationValidations.add(new UserRegistrationImplTestValidation(false));
        UserRegistrationDTO dto = new UserRegistrationDTO();
        Mockito.when(converter.convertToEntity(dto)).thenReturn(new UserAccount());

        assertThrows(ValidationBusinessException.class, () -> {
            userAccountService.registerUser(dto);
        });
    }

    @Test
    void testRegisterUserCallSaveToRepo() throws ValidationBusinessException {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        Mockito.when(converter.convertToEntity(dto)).thenReturn(new UserAccount());

        userAccountService.registerUser(dto);

        Mockito.verify(userAccountRepository, Mockito.times(1)).save(entityCaptor.capture());
    }
}