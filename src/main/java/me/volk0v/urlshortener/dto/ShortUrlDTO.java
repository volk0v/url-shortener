package me.volk0v.urlshortener.dto;

import jakarta.validation.constraints.NotEmpty;

public class ShortUrlDTO {

    @NotEmpty(message = "referenceUrl can't be empty")
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
