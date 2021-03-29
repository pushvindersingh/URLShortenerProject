package com.url.shortner.test.data;

import com.url.shortner.model.Url;

import java.time.LocalDateTime;

/**
 * A utility class that contains the test data.
 */
public class TestUrlData {
    public static Url testUrl() {
        return new Url("http://www.longdummyurl/", "aF76H", LocalDateTime.now(),LocalDateTime.now().plusSeconds(60));
    }

    /**
     * Avoid instantiation
     */
    private TestUrlData() {

    }
}
