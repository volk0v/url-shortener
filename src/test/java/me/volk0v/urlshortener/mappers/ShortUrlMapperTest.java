package me.volk0v.urlshortener.mappers;

import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.models.ShortUrl;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;

class ShortUrlMapperTest {

    private final ShortUrlMapper mapper = new ShortUrlMapper(new ModelMapper());

    @Test
    public void givenDto_whenMapToModel_thenReturnModel() {
        ShortUrlDTO dto = new ShortUrlDTO("https://ya.ru", "yandex");

        ShortUrl model = mapper.toModel(dto);

        assertThat(model.getReferenceUrl()).isEqualTo(dto.getReferenceUrl());
        assertThat(model.getShortenedName()).isEqualTo(dto.getShortenedName());
    }

    @Test
    public void givenModel_whenMapToDto_thenReturnDto() {
        ShortUrl model = new ShortUrl("https://ya.ru", "yandex");

        ShortUrlDTO dto = mapper.toDto(model);

        assertThat(dto.getReferenceUrl()).isEqualTo(model.getReferenceUrl());
        assertThat(dto.getShortenedName()).isEqualTo(model.getShortenedName());
    }

}