package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessException;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountServiceImplGetUserDetailsTest {

    private static final Date DEFAULT_DATE = new Date();

    @Mock
    private UserAccountRepository userAccountRepository;

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
    void getUserDetailsOkUserFound() {
        long provided = 200L;
        UserDetailsDTO expected = getDefaultUserDetailsDTO();
        UserAccount userFound = getDefaultUserAccountEntity();
        Mockito.when(userAccountRepository.findById(provided)).thenReturn(Optional.of(userFound));

        assertDoesNotThrow(() -> {
            UserDetailsDTO actual = userAccountService.getUserDetails(provided);
            assertEquals(expected, actual);
        });
        Mockito.verify(userAccountRepository, Mockito.times(1)).findById(provided);
    }

    @Test
    void getUserDetailsKoUserNotFound() {
        long id = 404L;
        Mockito.when(userAccountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            userAccountService.getUserDetails(id);
        });
        Mockito.verify(userAccountRepository, Mockito.times(1)).findById(id);
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

    private UserAccount getDefaultUserAccountEntity() {
        UserAccount entity = new UserAccount();
        entity.setUserName("username");
        entity.setBirthDate(DEFAULT_DATE);
        entity.setCountryCode("fr");
        entity.setGender("F");
        entity.setPhoneNumber("0456897721");

        return entity;
    }
}