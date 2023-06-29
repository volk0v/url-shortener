package me.volk0v.urlshortener.exceptions;

public class ShortenedNameNotFoundException extends RuntimeException {

    public ShortenedNameNotFoundException(String message) {
        super(message);
    }

}
