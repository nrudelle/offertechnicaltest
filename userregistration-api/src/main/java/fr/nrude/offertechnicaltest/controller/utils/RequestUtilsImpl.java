package fr.nrude.offertechnicaltest.controller.utils;

import fr.nrude.offertechnicaltest.controller.utils.RequestUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestUtilsImpl implements RequestUtils {
    public List<String> getErrorsList(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
