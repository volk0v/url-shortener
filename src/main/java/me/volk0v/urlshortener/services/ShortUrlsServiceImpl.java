package me.volk0v.urlshortener.services;

import me.volk0v.urlshortener.dto.ShortUrlDTO;
import me.volk0v.urlshortener.generators.RandomStringGenerator;
import me.volk0v.urlshortener.models.ShortUrl;
import me.volk0v.urlshortener.repositories.ShortUrlsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShortUrlsServiceImpl implements ShortUrlsService {

    @Value("${url-shortener.random-url-length}")
    private int RANDOM_STRING_LENGTH;

    private final ShortUrlsRepository repository;
    private final RandomStringGenerator randomStringGenerator;

    public ShortUrlsServiceImpl(ShortUrlsRepository repository, RandomStringGenerator randomStringGenerator) {
        this.repository = repository;
        this.randomStringGenerator = randomStringGenerator;
    }

    @Override
    @Transactional
    public Optional<ShortUrl> register(ShortUrlDTO dto) {
        String referenceUrl = dto.getReferenceUrl();
        String shortenedUrl = dto.getShortenedName();

        if (shortenedUrl.isEmpty()) {
            shortenedUrl = randomStringGenerator.generate(RANDOM_STRING_LENGTH);
        }

        ShortUrl shortUrl = new ShortUrl(referenceUrl, shortenedUrl);

        repository.save(shortUrl);

        return Optional.of(shortUrl);
    }

    @Override
    public Optional<String> findByShortenedName(String shortenedName) {
        return repository.findByShortenedUrl(shortenedName)
                .flatMap(shortUrl -> Optional.of(shortUrl.getReferenceUrl()));
    }

}
