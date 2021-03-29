/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.url.shortner.controller;
/**
 * @author Pushvinder
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.url.shortner.model.Url;
import com.url.shortner.model.UrlDto;
import com.url.shortner.model.UrlErrorResponse;
import com.url.shortner.model.UrlResponseDto;
import com.url.shortner.service.URLService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class URLController {
    @Autowired
    private URLService urlService;

    @PostMapping("/generate")
    public ResponseEntity<?> createShortLink(@RequestBody UrlDto urlDto) {
        Url urlToRet = urlService.createUrl(urlDto);

        if (urlToRet != null) {
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
            urlResponseDto.setExpirationDate(urlToRet.getExpirationTime());
            urlResponseDto.setShortLink(urlToRet.getShortLink());
            return new ResponseEntity<>(urlResponseDto, HttpStatus.OK);
        }

        UrlErrorResponse urlErrorResponseDto = new UrlErrorResponse();
        urlErrorResponseDto.setStatus("500");
        urlErrorResponseDto.setError("There was an error processing your request. please try again.");
        return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) throws IOException {

        if (StringUtils.isEmpty(shortLink)) {
            UrlErrorResponse urlErrorResponseDto = new UrlErrorResponse();
            urlErrorResponseDto.setError("Invalid Url");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }
        Url urlToRet = urlService.getUrlByShortLink(shortLink);

        if (urlToRet == null) {
            UrlErrorResponse urlErrorResponseDto = new UrlErrorResponse();
            urlErrorResponseDto.setError("Url does not exist or it might have expired!");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }

        if (urlToRet.getExpirationTime().isBefore(LocalDateTime.now())) {
            urlService.deleteUrl(urlToRet);
            UrlErrorResponse urlErrorResponseDto = new UrlErrorResponse();
            urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
            urlErrorResponseDto.setStatus("200");
            return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
        }

        response.sendRedirect(urlToRet.getOriginalUrl());
        return null;
    }

}
