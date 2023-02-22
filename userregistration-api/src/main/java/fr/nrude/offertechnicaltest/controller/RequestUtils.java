package fr.nrude.offertechnicaltest.controller;

import org.springframework.validation.BindingResult;

import java.util.List;

public interface RequestUtils {
    List<String> getErrorsList(BindingResult bindingResult);
}
