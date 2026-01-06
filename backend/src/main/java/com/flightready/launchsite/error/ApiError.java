package com.flightready.launchsite.error;

import java.util.List;

public class ApiError {
    private String message;
    private List<FieldError> errors;

    public ApiError(String message, List<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public static class FieldError {
        private String field;
        private String error;

        public FieldError(String field, String error) {
            this.field = field;
            this.error = error;
        }

        public String getField() {
            return field;
        }

        public String getError() {
            return error;
        }
    }
}
