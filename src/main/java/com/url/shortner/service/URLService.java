/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.url.shortner.service;
/**
 * @author Pushvinder
 */

import com.google.common.hash.Hashing;
import com.url.shortner.model.Url;
import com.url.shortner.model.UrlDto;
import com.url.shortner.repository.URLRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class URLService {
    public static final long EXPIRATION_TIME = MINUTES.toSeconds(2);

    @Autowired
    private URLRepository urlRepository;

    public Url createUrl(UrlDto urlDto) {
        if (Objects.nonNull(urlDto) && StringUtils.isNotEmpty(urlDto.getUrl())) {
            Url url = new Url();
            url.setCreationTime(LocalDateTime.now());
            url.setOriginalUrl(urlDto.getUrl());
            url.setShortLink(encodeUrl(urlDto.getUrl()));
            url.setExpirationTime(getExpirationDate(urlDto.getExpirationDate(), url.getCreationTime()));
            return urlRepository.save(url);
        }
        return null;
    }

    public Url getUrlByShortLink(String shortLink) {
        return urlRepository.findByShortLink(shortLink);
    }

    public void deleteUrl(Url url) {
        urlRepository.delete(url);
    }

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationTime) {
        if (StringUtils.isBlank(expirationDate)) {
            return creationTime.plusSeconds(EXPIRATION_TIME);
        }
        return LocalDateTime.parse(expirationDate);
    }

    private String encodeUrl(String url) {
        return Hashing.murmur3_32()
                .hashString(url.concat(LocalDateTime.now().toString()), StandardCharsets.UTF_8)
                .toString();
    }
}
