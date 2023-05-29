package me.volk0v.urlshortener.validators;

import me.volk0v.urlshortener.services.ShortUrlsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ShortenedNameValidator implements Validator {

    private final ShortUrlsService service;

    public ShortenedNameValidator(ShortUrlsService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String shortenedName = (String) target;

        if (service.findByShortenedName(shortenedName).isPresent()) {
            errors.rejectValue("shortenedName", "", "The shortened name is already presented");
        }

        if (shortenedName.contains(" ")) {
            errors.rejectValue("shortenedName", "", "Shortened name shouldn't contain spaces");
        }
    }

}
