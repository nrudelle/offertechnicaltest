package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.UserAccountService;
import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessValidationException;
import fr.nrude.offertechnicaltest.business.exceptions.ResourceNotFoundBusinessException;
import fr.nrude.offertechnicaltest.controller.dto.RequestResult;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;
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
    private UserAccountService userAccountService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<RequestResult<UserDetailsDTO>> registerUser(@Valid @RequestBody UserRegistrationRequest request,
                                                      BindingResult bindingResult) {
        ResponseEntity<RequestResult<UserDetailsDTO>> response;

        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = RequestUtils.getErrorsList(bindingResult);
                RequestResult<UserDetailsDTO> result = new RequestResult<>(null, errors);
                return ResponseEntity.badRequest().body(result);
            }

            UserRegistrationDTO businessDTO = ConverterUtils.convertToRegistrationDTO(request);
            UserDetailsDTO userCreatedDetailsDTO = userAccountService.registerUser(businessDTO);

            RequestResult<UserDetailsDTO> result = new RequestResult<>(userCreatedDetailsDTO);
            response = ResponseEntity.ok(result);
        } catch(IllegalArgumentException e) {
            RequestResult<UserDetailsDTO> result = new RequestResult<>(null, Collections.singletonList(e.getMessage()));
            response = ResponseEntity.badRequest().body(result);
        } catch (BusinessValidationException e) {
            RequestResult<UserDetailsDTO> result = new RequestResult<>(null, e.getValidationErrorMessages());
            return ResponseEntity.internalServerError().body(result);
        }

        return response;
    }

    @GetMapping("/{id}/getDetails")
    @ResponseBody
    public ResponseEntity<RequestResult<UserDetailsDTO>> getUserDetails(@PathVariable("id") long id) {
        UserDetailsDTO userDetails = null;
        ResponseEntity<RequestResult<UserDetailsDTO>> response;
        try {
            userDetails = userAccountService.getUserDetails(id);
            RequestResult<UserDetailsDTO> result = new RequestResult<>(userDetails);
            response = ResponseEntity.ok(result);
        } catch (ResourceNotFoundBusinessException e) {
            RequestResult<UserDetailsDTO> result = new RequestResult<>(null);
            result.errors.add(e.getMessage());
            response = ResponseEntity.internalServerError().body(result);
        }

        return response;
    }
}
