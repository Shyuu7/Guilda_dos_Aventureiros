package br.com.infnet.dr1tp1.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private final List<String> errors;
    @Getter
    private final boolean valid;

    public ValidationResult(List<String> errors) {
        this.errors = new ArrayList<>(errors);
        this.valid = errors.isEmpty();
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public String getErrorMessage() {
        return String.join("; ", errors);
    }
}
