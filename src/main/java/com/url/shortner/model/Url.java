/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.url.shortner.model;
/**
 * Reference: https://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html#:%7E:text=An%20entity%20is%20a%20lightweight,entities%20can%20use%20helper%20classes.
 *
 * @author Pushvinder
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
public class Url {
    @Id
    @GeneratedValue
    private long id;
    @Lob
    private String originalUrl;
    private LocalDateTime creationTime;
    private String shortLink;
    private LocalDateTime expirationTime;

    public Url() {
    }

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Url(String originalUrl, String shortLink, LocalDateTime creationTime, LocalDateTime expirationTime) {
        this.originalUrl = originalUrl;
        this.shortLink = shortLink;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", OriginalUrl='" + originalUrl + '\'' +
                ", Short Link='" + shortLink + '\'' +
                ", Creation Time=" + creationTime +
                ", Expiration Time=" + expirationTime +
                '}';
    }
}