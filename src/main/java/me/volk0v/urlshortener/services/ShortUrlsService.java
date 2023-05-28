package me.volk0v.urlshortener.services;

import me.volk0v.urlshortener.models.ShortUrl;

import java.util.Optional;

public interface ShortUrlsService {

    Optional<ShortUrl> register();

    Optional<String> findByShortenedUrl(String shortenedUrl);

}
