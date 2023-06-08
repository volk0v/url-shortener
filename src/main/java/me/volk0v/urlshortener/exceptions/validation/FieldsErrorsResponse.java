package me.volk0v.urlshortener.exceptions.validation;

import java.util.List;

public class FieldsErrorsResponse {

    private List<ShortFieldError> errors;
    private long timestamp;

    public FieldsErrorsResponse(List<ShortFieldError> errors, long timestamp) {
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public List<ShortFieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<ShortFieldError> errors) {
        this.errors = errors;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
