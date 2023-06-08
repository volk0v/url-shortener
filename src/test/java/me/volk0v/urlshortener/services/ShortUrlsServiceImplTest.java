package me.volk0v.urlshortener.services;

import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.generators.RandomStringGenerator;
import me.volk0v.urlshortener.models.ShortUrl;
import me.volk0v.urlshortener.repositories.ShortUrlsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ShortUrlsServiceImplTest {

    private final ShortUrlsRepository repository = Mockito.mock(ShortUrlsRepository.class);
    private final RandomStringGenerator randomStringGenerator = Mockito.mock(RandomStringGenerator.class);

    @Test
    public void givenDtoWithCustomName_whenRegister_thenReturnModel() {
        ShortUrlsService service = new ShortUrlsServiceImpl(repository, randomStringGenerator);
        ShortUrlDTO dto = new ShortUrlDTO("https://ya.ru/", "yandex");

        Optional<ShortUrl> shortUrlOpt = service.register(dto);

        verify(repository, times(1)).save(any());
        verify(randomStringGenerator, times(0)).generate(anyInt());

        assertThat(shortUrlOpt).isPresent();

        ShortUrl shortUrl = shortUrlOpt.get();

        assertThat(shortUrl.getReferenceUrl()).isEqualTo(dto.getReferenceUrl());
        assertThat(shortUrl.getShortenedName()).isEqualTo(dto.getShortenedName());
    }

    @Test
    public void givenDtoWithoutCustomName_whenRegister_thenReturnModel() {
        ShortUrlsService service = new ShortUrlsServiceImpl(repository, randomStringGenerator);
        ShortUrlDTO dto = new ShortUrlDTO("https://ya.ru/", "");

        String randomGeneratedName = "123qwe7";
        when(randomStringGenerator.generate(anyInt())).thenReturn(randomGeneratedName);

        Optional<ShortUrl> shortUrlOpt = service.register(dto);

        verify(repository, times(1)).save(any());
        verify(randomStringGenerator, times(1)).generate(anyInt());

        assertThat(shortUrlOpt).isPresent();

        ShortUrl shortUrl = shortUrlOpt.get();

        assertThat(shortUrl.getReferenceUrl()).isEqualTo(dto.getReferenceUrl());
        assertThat(shortUrl.getShortenedName()).isEqualTo(randomGeneratedName);
    }

    @Test
    public void givenShortUrlInDatabase_whenFindByShortname_returnReferenceUrl() {
        String referenceUrl = "https://ya.ru/";
        String shortenedName = "yandex";

        ShortUrl shortUrl = new ShortUrl(referenceUrl, shortenedName);
        when(repository.findByShortenedUrl(shortUrl.getShortenedName())).thenReturn(Optional.of(shortUrl));

        ShortUrlsService service = new ShortUrlsServiceImpl(repository, randomStringGenerator);

        Optional<String> actual = service.findByShortenedName(shortenedName);

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(referenceUrl);
    }

}