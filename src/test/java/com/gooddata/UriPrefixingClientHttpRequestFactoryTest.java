/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UriPrefixingClientHttpRequestFactoryTest {
    private final ClientHttpRequestFactory requestFactory = new UriPrefixingClientHttpRequestFactory(
            new SimpleClientHttpRequestFactory(), "http", "localhost", 1234
    );

    @Test
    public void createRequest() throws IOException {
        final ClientHttpRequest request = requestFactory.createRequest(URI.create("/gdc/resource"), HttpMethod.GET);
        assertThat(request.getURI().toString(), is("http://localhost:1234/gdc/resource"));
    }
}
