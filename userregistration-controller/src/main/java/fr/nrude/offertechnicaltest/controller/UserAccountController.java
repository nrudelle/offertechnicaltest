package fr.nrude.offertechnicaltest.controller;

import fr.nrude.offertechnicaltest.business.UserAccountService;
import fr.nrude.offertechnicaltest.business.dto.UserDetailsDTO;
import fr.nrude.offertechnicaltest.business.dto.UserRegistrationDTO;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessException;
import fr.nrude.offertechnicaltest.business.exceptions.BusinessValidationException;
import fr.nrude.offertechnicaltest.controller.dto.RequestResult;
import fr.nrude.offertechnicaltest.controller.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<RequestResult<Boolean>> registerUser(@Valid @RequestBody UserRegistrationRequest request,
                                                      BindingResult bindingResult) {
        ResponseEntity<RequestResult<Boolean>> response;

        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = RequestUtils.getErrorsList(bindingResult);
                RequestResult<Boolean> result = new RequestResult<>(false, errors);
                return ResponseEntity.badRequest().body(result);
            }

            UserRegistrationDTO businessDTO = ConverterUtils.convertToRegistrationDTO(request);
            userAccountService.registerUser(businessDTO);

            RequestResult<Boolean> result = new RequestResult<>(true);
            response = ResponseEntity.ok(result);
        } catch(IllegalArgumentException e) {
            RequestResult<Boolean> result = new RequestResult<>(false);
            result.errors.add(e.getMessage());
            response = ResponseEntity.badRequest().body(result);
        } catch (BusinessValidationException e) {
            RequestResult<Boolean> result = new RequestResult<>(false, e.getValidationErrorMessages());
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
        } catch (BusinessException e) {
            RequestResult<UserDetailsDTO> result = new RequestResult<>(null);
            result.errors.add(e.getMessage());
            response = ResponseEntity.internalServerError().body(result);
        }

        return response;
    }
}
