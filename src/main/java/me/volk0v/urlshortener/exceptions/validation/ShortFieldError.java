package me.volk0v.urlshortener.exceptions.validation;

public class ShortFieldError {

    private String fieldName;
    private String error;

    public ShortFieldError(String fieldName, String error) {
        this.fieldName = fieldName;
        this.error = error;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
