package fr.nrude.offertechnicaltest.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestResultDTO<T> {
    public T result;
    public List<String> errors = new ArrayList<>();

    public RequestResultDTO(T result) {
        this(result, null);
    }
    public RequestResultDTO(T result, List<String> errors) {
        this.result = result;
        if(errors == null) {
            errors = new ArrayList<>();
        }

        this.errors = errors;
    }
}
