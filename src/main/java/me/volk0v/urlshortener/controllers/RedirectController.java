package me.volk0v.urlshortener.controllers;

import me.volk0v.urlshortener.exceptions.ShortenedNameNotFoundException;
import me.volk0v.urlshortener.services.ShortUrlsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Controller
public class RedirectController {

    private final ShortUrlsService service;

    public RedirectController(ShortUrlsService service) {
        this.service = service;
    }

    @GetMapping("/{shortened-url}")
    public String redirectToUrl(@PathVariable("shortened-url") String shortenedUrl) {
        String redirectUrl = service.findByShortenedName(shortenedUrl).orElseThrow(
                () -> new ShortenedNameNotFoundException(
                        "Reference url hasn't been found for shortened name '" + shortenedUrl + "'"
                )
        );

        return "redirect:" + redirectUrl;
    }

    public record ShortenedNameNotFoundResponse(String error, Date timestamp) {
    }

    @ExceptionHandler
    public ResponseEntity<ShortenedNameNotFoundResponse> handleException(ShortenedNameNotFoundException exception) {
        ShortenedNameNotFoundResponse response = new ShortenedNameNotFoundResponse(
                exception.getMessage(),
                new Date()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
