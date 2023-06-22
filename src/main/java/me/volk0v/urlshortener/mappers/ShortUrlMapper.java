package me.volk0v.urlshortener.mappers;

import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.models.ShortUrl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    private final ModelMapper mapper;

    public ShortUrlMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ShortUrl toModel(ShortUrlDTO dto) {
        return mapper.map(dto, ShortUrl.class);
    }

    public ShortUrlDTO toDto(ShortUrl model) {
        return mapper.map(model, ShortUrlDTO.class);
    }

}
