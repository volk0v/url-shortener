package me.volk0v.urlshortener.models;

import jakarta.persistence.*;

@Entity
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reference_url")
    private String referenceUrl;

    @Column(name = "shortened_url")
    private String shortenedUrl;

    public ShortUrl() {
    }

    public ShortUrl(String referenceUrl, String shortenedUrl) {
        this.referenceUrl = referenceUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
