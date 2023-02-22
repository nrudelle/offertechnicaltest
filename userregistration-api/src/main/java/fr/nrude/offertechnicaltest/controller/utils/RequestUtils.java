package fr.nrude.offertechnicaltest.controller.utils;

import org.springframework.validation.BindingResult;

import java.util.List;

public interface RequestUtils {
    List<String> getErrorsList(BindingResult bindingResult);
}
