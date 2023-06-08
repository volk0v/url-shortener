package me.volk0v.urlshortener.services;

import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.models.ShortUrl;

import java.util.Optional;

public interface ShortUrlsService {

    Optional<ShortUrl> register(ShortUrlDTO dto);

    Optional<String> findByShortenedName(String shortenedName);

}
