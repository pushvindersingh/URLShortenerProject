/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.url.shortner.service;

import com.url.shortner.model.Url;
import com.url.shortner.model.UrlDto;
import com.url.shortner.repository.URLRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.url.shortner.test.data.TestUrlData.testUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author Pushvinder
 */
@ExtendWith(MockitoExtension.class)
public class URLServiceTest {
    @Mock
    private URLRepository urlRepository;

    @InjectMocks
    private URLService urlservice;


    @Test
    void testGenerateShortWithNull() {
        assertNull(urlservice.createUrl(null));
    }

    @Test
    void testGenerateShortWithNullUrl() {
        assertNull(urlservice.createUrl(new UrlDto()));
    }

    @Test
    void testGenerateShortWithEmptyUrl() {
        assertNull(urlservice.createUrl(new UrlDto("")));
    }

    @Test
    void testGenerateShortWithUrl() {
        Url url = testUrl();
        when(urlRepository.save(Mockito.any(Url.class))).thenReturn(url);
        assertEquals(url.getOriginalUrl(), urlservice.createUrl(new UrlDto(url.getOriginalUrl())).getOriginalUrl());
    }

    @Test
    void testGetUrl() {
        Url url = testUrl();
        when(urlRepository.findByShortLink(url.getShortLink())).thenReturn(url);
        assertEquals(url,urlservice.getUrlByShortLink(url.getShortLink()));
    }

    @Test
    void testDeleteShort() {
        Url url = testUrl();
        urlservice.deleteUrl(url);
        verify(urlRepository, times(1)).delete(url);
    }
}
