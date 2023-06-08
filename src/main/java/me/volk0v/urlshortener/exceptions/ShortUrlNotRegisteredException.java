package me.volk0v.urlshortener.exceptions;

import org.springframework.validation.BindingResult;

public class ShortUrlNotRegisteredException extends RuntimeException {

    private BindingResult bindingResult;

    public ShortUrlNotRegisteredException(String message) {
        super(message);
    }

    public ShortUrlNotRegisteredException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

}
