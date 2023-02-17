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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

            UserRegistrationDTO businessDTO = convertToRegistrationDTO(request);
            userAccountService.registerUser(businessDTO);

            RequestResult<Boolean> result = new RequestResult<>(true);
            response = ResponseEntity.ok(result);
        } catch(DateTimeException e) {
            RequestResult<Boolean> result = new RequestResult<>(false);
            result.errors.add("Invalid date : "+e.getMessage());
            response = ResponseEntity.badRequest().body(result);
        } catch (BusinessValidationException e) {
            RequestResult<Boolean> result = new RequestResult<>(false, e.getValidationErrorMessages());
            return ResponseEntity.internalServerError().body(result);
        }

        return response;
    }

    private UserRegistrationDTO convertToRegistrationDTO(UserRegistrationRequest request) {
        UserRegistrationDTO dto = new UserRegistrationDTO();

        dto.userName = request.userName;
        LocalDate birthDate = LocalDate.parse(request.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dto.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dto.countryCode = request.countryCode;
        dto.gender = request.gender;
        dto.phoneNumber = request.phoneNumber;

        return dto;
    }

    @GetMapping("/{id}/getDetails")
    @ResponseBody
    public ResponseEntity<RequestResult<UserDetailsDTO>> getUserDetails(@PathVariable("id") Long id) {
        UserDetailsDTO userDetails = null;
        ResponseEntity<RequestResult<UserDetailsDTO>> response;
        try {
            userDetails = userAccountService.getUserDetails(id);
            RequestResult<UserDetailsDTO> result = new RequestResult<>(userDetails);
            response = ResponseEntity.ok(result);
        } catch (BusinessException e) {
            RequestResult<UserDetailsDTO> result = new RequestResult<>(null);
            result.errors.add(e.getMessage());
            response = ResponseEntity.badRequest().body(result);
        }

        return response;
    }
}
