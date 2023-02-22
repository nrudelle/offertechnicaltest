package fr.nrude.offertechnicaltest.controller.utils;

import fr.nrude.offertechnicaltest.controller.utils.RequestUtils;
import fr.nrude.offertechnicaltest.controller.utils.RequestUtilsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestUtilsImplTest {

    @Mock
    private BindingResult bindingResult;

    private static final RequestUtils REQUEST_UTILS = new RequestUtilsImpl();

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
    void testGetErrorsListOkHasErrors() {
        ObjectError objError1 = Mockito.mock(ObjectError.class);
        ObjectError objError2 = Mockito.mock(ObjectError.class);
        Mockito.when(objError1.getDefaultMessage()).thenReturn("error1");
        Mockito.when(objError2.getDefaultMessage()).thenReturn("error2");
        List<ObjectError> errorsProvided = Arrays.asList(objError1, objError2);
        Mockito.when(bindingResult.getAllErrors()).thenReturn(errorsProvided);

        List<String> actual = REQUEST_UTILS.getErrorsList(bindingResult);

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals("error1", actual.get(0));
        assertEquals("error2", actual.get(1));
    }
}