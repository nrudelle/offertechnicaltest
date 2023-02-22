package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.UserAccountService;
import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.ValidationBusinessException;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.controller.converter.ControllerConverterUtils;
import fr.nrude.offertechnicaltest.controller.dto.RequestResultDTO;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequestDTO;
import fr.nrude.offertechnicaltest.controller.utils.RequestUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAccountController {
    @Autowired
    private RequestUtils requestUtils;

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ControllerConverterUtils converter;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<RequestResultDTO<UserDetailsDTO>> registerUser(
            @Valid @RequestBody UserRegistrationRequestDTO request,
            BindingResult bindingResult) {

        ResponseEntity<RequestResultDTO<UserDetailsDTO>> response;

        try {
            if (!bindingResult.hasErrors()) {
                RequestResultDTO<UserDetailsDTO> result = processRegisterUser(request);
                response = ResponseEntity.ok(result);
            } else {
                RequestResultDTO<UserDetailsDTO> result = processRequestErrors(bindingResult);
                response = ResponseEntity.badRequest().body(result);
            }
        } catch(IllegalArgumentException e) {
            RequestResultDTO<UserDetailsDTO> result = new RequestResultDTO<>(null, Collections.singletonList(e.getMessage()));
            response = ResponseEntity.badRequest().body(result);
        } catch (ValidationBusinessException e) {
            RequestResultDTO<UserDetailsDTO> result = new RequestResultDTO<>(null, e.getValidationErrorMessages());
            response = ResponseEntity.internalServerError().body(result);
        }

        return response;
    }
    private RequestResultDTO<UserDetailsDTO> processRequestErrors(BindingResult bindingResult) {
        List<String> errors = requestUtils.getErrorsList(bindingResult);
        return new RequestResultDTO<>(null, errors);
    }
    private RequestResultDTO<UserDetailsDTO> processRegisterUser(UserRegistrationRequestDTO request) throws ValidationBusinessException {
        UserRegistrationDTO businessDTO = converter.convertToRegistrationDTO(request);
        UserDetailsDTO userCreatedDetailsDTO = userAccountService.registerUser(businessDTO);
        return new RequestResultDTO<>(userCreatedDetailsDTO);
    }

    @GetMapping("/{id}/getDetails")
    @ResponseBody
    public ResponseEntity<RequestResultDTO<UserDetailsDTO>> getUserDetails(@PathVariable("id") long id) {
        ResponseEntity<RequestResultDTO<UserDetailsDTO>> response;
        try {
            RequestResultDTO<UserDetailsDTO> result = processRegisterUser(id);
            response = ResponseEntity.ok(result);
        } catch (ResourceNotFoundBusinessException e) {
            RequestResultDTO<UserDetailsDTO> result = new RequestResultDTO<>(null,
                    Collections.singletonList(e.getMessage()));
            response = ResponseEntity.internalServerError().body(result);
        }

        return response;
    }
    private RequestResultDTO<UserDetailsDTO> processRegisterUser(long id) throws ResourceNotFoundBusinessException {
        UserDetailsDTO userDetails = userAccountService.getUserDetails(id);
        return new RequestResultDTO<>(userDetails);
    }
}
