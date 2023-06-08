package me.volk0v.urlshortener.controllers;

import jakarta.validation.Valid;
import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.exceptions.ShortUrlNotRegisteredException;
import me.volk0v.urlshortener.exceptions.validation.FieldsErrorsResponse;
import me.volk0v.urlshortener.exceptions.validation.ShortFieldError;
import me.volk0v.urlshortener.models.ShortUrl;
import me.volk0v.urlshortener.services.ShortUrlsService;
import me.volk0v.urlshortener.validators.ShortenedNameValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class RegistrationController {

    private final ShortUrlsService service;
    private final ShortenedNameValidator shortenedNameValidator;

    public RegistrationController(ShortUrlsService service, ShortenedNameValidator shortenedNameValidator) {
        this.service = service;
        this.shortenedNameValidator = shortenedNameValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid ShortUrlDTO dto,
                                           BindingResult bindingResult) {
        String customShortenedName = dto.getShortenedName();
        if (!customShortenedName.isEmpty()) {
            shortenedNameValidator.validate(customShortenedName, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            throw new ShortUrlNotRegisteredException(bindingResult);
        }

        Optional<ShortUrl> shortUrlOpt = service.register(dto);

        ShortUrl shortUrl = shortUrlOpt.orElseThrow(() ->
                new ShortUrlNotRegisteredException("Something wrong with saving short URL, maybe shortened name isn't unique")
        );

        return new ResponseEntity<>(shortUrl.getShortenedName(), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<FieldsErrorsResponse> handleException(ShortUrlNotRegisteredException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        if (bindingResult == null) {
            return new ResponseEntity<>(
                    new FieldsErrorsResponse(Collections.emptyList(), System.currentTimeMillis()),
                    HttpStatus.BAD_REQUEST
            );
        }

        List<ShortFieldError> errors = new ArrayList<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(new ShortFieldError(error.getField(), error.getDefaultMessage()));
        }

        return new ResponseEntity<>(
                new FieldsErrorsResponse(errors, System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST
        );
    }

}
