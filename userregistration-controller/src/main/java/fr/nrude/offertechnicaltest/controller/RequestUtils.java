package fr.nrude.offertechnicaltest.controller;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class RequestUtils {
    public static List<String> getErrorsList(BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return errors;
    }
}
