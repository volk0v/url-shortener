package me.volk0v.urlshortener.generators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RandomAlphanumericStringGeneratorTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 7, 10})
    public void givenLength_whenGenerateString_thenReturnString(int length) {
        RandomAlphanumericStringGenerator stringGenerator = new RandomAlphanumericStringGenerator();

        String randomString = stringGenerator.generate(length);

        System.out.println(randomString);

        assertThat(randomString).isNotEmpty();
        assertThat(randomString).isAlphanumeric();
        assertThat(randomString).isLowerCase();
        assertThat(randomString.length()).isEqualTo(length);
    }

}