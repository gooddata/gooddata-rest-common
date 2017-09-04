/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rest.common;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UriPrefixingClientHttpRequestFactoryTest {
    private static final int PORT = 1234;
    private final ClientHttpRequestFactory requestFactory = new UriPrefixingClientHttpRequestFactory(
            new HttpComponentsClientHttpRequestFactory(), "localhost", PORT, "http"
    );

    @Test
    public void createRequest() throws IOException {
        final ClientHttpRequest request = requestFactory.createRequest(URI.create("/gdc/abc"), HttpMethod.GET);
        assertThat(request.getURI().toString(), is("http://localhost:1234/gdc/abc"));
    }
}
