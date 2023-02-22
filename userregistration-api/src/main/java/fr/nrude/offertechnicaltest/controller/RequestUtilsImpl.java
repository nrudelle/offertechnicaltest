package fr.nrude.offertechnicaltest.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestUtilsImpl implements RequestUtils {
    public List<String> getErrorsList(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return errors;
    }
}
