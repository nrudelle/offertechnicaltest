package fr.nrude.offertechnicaltest.business;

import fr.nrude.offertechnicaltest.business.converter.BusinessConverterUtils;
import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.dao.entities.UserAccount;
import fr.nrude.offertechnicaltest.dao.repository.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountServiceImplGetUserDetailsTest {

    private static final Date DEFAULT_DATE = new Date();

    @Mock
    private UserAccountRepository userAccountRepository;

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
    void testGetUserDetailsOkUserFound() {
        long provided = 200L;
        UserAccount userFound = getDefaultUserAccountEntity();
        UserDetailsDTO expected = getDefaultUserDetailsDTO();
        Mockito.when(userAccountRepository.findById(provided)).thenReturn(Optional.of(userFound));
        Mockito.when(converter.convertToDTO(userFound)).thenReturn(expected);

        assertDoesNotThrow(() -> {
            UserDetailsDTO actual = userAccountService.getUserDetails(provided);
            assertEquals(expected, actual);
        });
        Mockito.verify(userAccountRepository, Mockito.times(1)).findById(provided);
    }

    @Test
    void testGetUserDetailsKoUserNotFound() {
        long id = 404L;
        Mockito.when(userAccountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundBusinessException.class, () -> {
            userAccountService.getUserDetails(id);
        });
        Mockito.verify(userAccountRepository, Mockito.times(1)).findById(id);
    }

    private UserDetailsDTO getDefaultUserDetailsDTO() {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.userName = "username";
        LocalDate birthLocalDate = LocalDate.ofInstant(DEFAULT_DATE.toInstant(), ZoneId.systemDefault());
        dto.birthDate = String.format("%2s-%2s-%s",
                birthLocalDate.getDayOfMonth(), birthLocalDate.getMonthValue(), birthLocalDate.getYear())
                .replace(' ', '0');
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