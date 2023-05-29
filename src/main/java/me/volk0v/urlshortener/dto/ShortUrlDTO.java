package me.volk0v.urlshortener.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class ShortUrlDTO {

    @NotEmpty(message = "referenceUrl can't be empty")
    @URL(message = "Reference URL should be URL")
    private String referenceUrl;

    @NotEmpty(message = "shortenedUrl can't be empty")
    private String shortenedName;

    public ShortUrlDTO() {
    }

    public ShortUrlDTO(String referenceUrl, String shortenedName) {
        this.referenceUrl = referenceUrl;
        this.shortenedName = shortenedName;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getShortenedName() {
        return shortenedName;
    }

    public void setShortenedName(String shortenedName) {
        this.shortenedName = shortenedName;
    }
}
