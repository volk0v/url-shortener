package me.volk0v.urlshortener.generators;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomAlphanumericStringGenerator implements RandomStringGenerator {

    @Override
    public String generate(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        // Filter is used to leave out Unicode characters between 65 and 90 in order to avoid out of range characters
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString()
                .toLowerCase();
    }

}
