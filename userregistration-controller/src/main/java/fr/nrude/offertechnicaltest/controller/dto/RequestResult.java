package fr.nrude.offertechnicaltest.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestResult<T> {
    public T result;
    public List<String> errors = new ArrayList<>();

    public RequestResult(T result) {
        this(result, null);
    }
    public RequestResult(T result, List<String> errors) {
        this.result = result;
        if(errors == null) {
            errors = new ArrayList<>();
        }

        this.errors = errors;
    }
}
