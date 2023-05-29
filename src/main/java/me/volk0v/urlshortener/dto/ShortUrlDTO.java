package me.volk0v.urlshortener.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class ShortUrlDTO {

    @NotEmpty(message = "referenceUrl can't be empty")
    @URL(message = "Reference URL should be URL")
    private String referenceUrl;

    @NotEmpty(message = "shortenedUrl can't be empty")
    private String shortenedUrl;

    public ShortUrlDTO() {
    }

    public ShortUrlDTO(String referenceUrl, String shortenedUrl) {
        this.referenceUrl = referenceUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }
}
