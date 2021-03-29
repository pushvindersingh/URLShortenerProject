package com.url.shortner.controller;

import com.url.shortner.model.Url;
import com.url.shortner.model.UrlDto;
import com.url.shortner.model.UrlErrorResponse;
import com.url.shortner.model.UrlResponseDto;
import com.url.shortner.service.URLService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static com.url.shortner.test.data.TestUrlData.testUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Pushvinder
 */
@ExtendWith(MockitoExtension.class)
class URLControllerTest {

    @Mock
    private URLService urlservice;

    @InjectMocks
    private URLController urlController;

    @Test
    void testCreateShortLinkWithNull() {
        when(urlservice.createUrl(null)).thenReturn(null);
        ResponseEntity<?> response = urlController.createShortLink(null);
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("There was an error processing your request. please try again.", ((UrlErrorResponse)response.getBody()).getError());
    }

    @Test
    void testCreateShortLinkWithUrl() {
        Url testUrl = testUrl();
        when(urlservice.createUrl(Mockito.any(UrlDto.class))).thenReturn(testUrl);
        ResponseEntity<?> response = urlController.createShortLink(new UrlDto());
        assertEquals(OK, response.getStatusCode());
        UrlResponseDto urlResponse = (UrlResponseDto) response.getBody();
        assertNotNull(urlResponse);
        assertEquals(testUrl.getShortLink(), urlResponse.getShortLink());
        assertEquals(testUrl.getOriginalUrl(), urlResponse.getOriginalUrl());
        assertEquals(testUrl.getExpirationTime(), urlResponse.getExpirationDate());
    }

}