package me.volk0v.urlshortener.controllers;

import me.volk0v.urlshortener.services.ShortUrlsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RedirectController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RedirectControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShortUrlsService service;

    @Test
    public void givenCorrectShortenedName_whenGoToPage_thenRedirectToReference() throws Exception {
        String shortenedName = "test";
        String reference = "test.example";

        when(service.findByShortenedName(any())).thenReturn(Optional.of(reference));

        mvc.perform(get("/" + shortenedName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(reference));
    }

    @Test
    public void givenNotExistingShortenedName_whenGoToPage_thenGetError() throws Exception {
        when(service.findByShortenedName(any())).thenReturn(Optional.empty());

        mvc.perform(get("/not-existing"))
                .andExpect(status().isBadRequest())
                .andExpectAll(
                        jsonPath("$.error").isNotEmpty(),
                        jsonPath("$.timestamp").exists()
                );
    }

}