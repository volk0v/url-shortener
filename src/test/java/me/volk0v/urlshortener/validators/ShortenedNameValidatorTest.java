package me.volk0v.urlshortener.validators;

import me.volk0v.urlshortener.services.ShortUrlsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ShortenedNameValidatorTest {

    @MockBean
    private ShortUrlsService service;

    @Test
    public void givenRightName_whenValidate_thenNoErrors() {
        when(service.findByShortenedName(any())).thenReturn(Optional.empty());

        testValidatingWithServiceAndNameAndTimesOfErrorInvocation(service, "right-name", 0);
    }

    @Test
    public void givenExistingName_whenValidate_thenOneErrorInvocation() {
        when(service.findByShortenedName(any())).thenReturn(Optional.of("existing"));

        testValidatingWithServiceAndNameAndTimesOfErrorInvocation(service, "right-name", 1);
    }

    @Test
    public void givenWrongName_whenValidate_thenOneErrorInvocation() {
        when(service.findByShortenedName(any())).thenReturn(Optional.empty());

        testValidatingWithServiceAndNameAndTimesOfErrorInvocation(service, "wrong name", 1);
    }

    private void testValidatingWithServiceAndNameAndTimesOfErrorInvocation(ShortUrlsService service, String name, int times) {
        ShortenedNameValidator validator = new ShortenedNameValidator(service);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        validator.validate(name, bindingResult);

        verify(bindingResult, times(times)).rejectValue(any(), any(), any());
    }

}