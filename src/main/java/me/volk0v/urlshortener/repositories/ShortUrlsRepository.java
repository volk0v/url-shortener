package me.volk0v.urlshortener.repositories;

import me.volk0v.urlshortener.models.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlsRepository extends JpaRepository<ShortUrl, Integer> {

    Optional<ShortUrl> findByShortenedUrl(String shortenedUrl);

}
