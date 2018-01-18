package com.company.project.tracker.exception;

import javafx.util.Pair;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputValidationException extends RuntimeException {

    private BindingResult result;
    private Map<String, List<String>> currentErrors;

    public InputValidationException(BindingResult result) {
        this.result = result;
    }

    public InputValidationException(Pair<String, String> currentErrors) {
        setError(currentErrors.getKey(), currentErrors.getValue());
    }

    public Map<String, List<String>> getErrors() {
        if (this.result == null) {
            return this.currentErrors;
        }

        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            setError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return this.currentErrors;
    }

    private void setError(String fieldName, String fieldError) {
        if (currentErrors == null) {
            currentErrors = new HashMap<>();
        }

        List<String> strings = currentErrors.computeIfAbsent(fieldName, k -> new ArrayList<>());
        strings.add(fieldError);
        
        currentErrors.put(fieldName, strings);
    }
}
