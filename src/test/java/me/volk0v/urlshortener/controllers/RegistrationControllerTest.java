package me.volk0v.urlshortener.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.mappers.ShortUrlMapper;
import me.volk0v.urlshortener.models.ShortUrl;
import me.volk0v.urlshortener.services.ShortUrlsService;
import me.volk0v.urlshortener.validators.ShortenedNameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShortUrlsService service;

    @MockBean
    private ShortenedNameValidator validator;

    @MockBean
    private static ShortUrlMapper mapper;

    @BeforeEach
    void setUp() {
        when(mapper.toDto(any())).thenAnswer(invocationOnMock -> {
            ShortUrl model = invocationOnMock.getArgument(0);
            return new ShortUrlDTO(model.getReferenceUrl(), model.getShortenedName());
        });

        when(mapper.toModel(any())).thenAnswer(invocationOnMock -> {
            ShortUrlDTO dto = invocationOnMock.getArgument(0);
            return new ShortUrl(dto.getReferenceUrl(), dto.getShortenedName());
        });
    }

    @Test
    public void givenRightRequestWithoutCustomName_whenRegister_thenReturnShortenedName() throws Exception {
        ShortUrlDTO dto = new ShortUrlDTO("https://ya.ru");
        String request = new ObjectMapper().writeValueAsString(dto);

        ShortUrl result = new ShortUrl(dto.getReferenceUrl(), "generated");
        when(service.register(any())).thenReturn(Optional.of(result));

        mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.referenceUrl", is(result.getReferenceUrl())),
                        jsonPath("$.shortenedName", is(result.getShortenedName()))
                );
    }

    @Test
    public void givenWrongReferenceUrls_whenRegister_thenReturnError() throws Exception {
        ShortUrlDTO dto = new ShortUrlDTO("ya.ru");
        String request = new ObjectMapper().writeValueAsString(dto);

        mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$", hasKey("timestamp")),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].fieldName", is("referenceUrl")),
                        jsonPath("$.errors[0].error", is("Reference URL should be URL"))
                );
    }

    @Test
    public void givenRightRequestWithCustomName_whenRegister_thenReturnShortenedName() throws Exception {
        ShortUrlDTO dto = new ShortUrlDTO("https://ya.ru", "yandex");
        String request = new ObjectMapper().writeValueAsString(dto);

        ShortUrl result = new ShortUrl(dto.getReferenceUrl(), dto.getShortenedName());
        when(service.register(any())).thenReturn(Optional.of(result));

        mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.referenceUrl", is(dto.getReferenceUrl())),
                        jsonPath("$.shortenedName", is(dto.getShortenedName()))
                );
    }

    @Test
    public void givenWrongRequestWithCustomName_whenRegister_thenReturnError() throws Exception {
        ShortUrlDTO dto = new ShortUrlDTO("https://ya.ru", "WRONG");
        String request = new ObjectMapper().writeValueAsString(dto);

        String fieldName = "shortenedName";
        String error = "The shortened name is already presented";
        doAnswer(invocationOnMock -> {
            Errors errors = invocationOnMock.getArgument(1);
            errors.rejectValue(fieldName, "", error);
            return null;
        }).when(validator).validate(any(), any());

        mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$", hasKey("timestamp")),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].fieldName", is(fieldName)),
                        jsonPath("$.errors[0].error", is(error))
                );
    }


}