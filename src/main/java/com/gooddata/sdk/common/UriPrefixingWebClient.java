/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.gooddata.sdk.common.util.Validate.notNull;

/**
 * Factory for creating HTTP requests using WebClient.
 * The factory allows you to specify hostname and port for Spring REST client which by default requires absolute URI.
 */
public class UriPrefixingWebClient {

    private final WebClient webClient;
    private final UriPrefixer prefixer;

    /**
     * Create an instance that uses the given {@link WebClient.Builder}
     * and URI prefix for all HTTP requests.
     *
     * @param webClientBuilder the WebClient builder
     * @param uriPrefix        the URI for setting hostname and port of all HTTP requests
     */
    public UriPrefixingWebClient(final WebClient.Builder webClientBuilder, final URI uriPrefix) {
        this(webClientBuilder, new UriPrefixer(uriPrefix));
    }

    /**
     * Create an instance that uses the given {@link WebClient.Builder}
     * and URI prefix for all HTTP requests.
     *
     * @param webClientBuilder the WebClient builder
     * @param uri              the URI for setting hostname and port of all HTTP requests
     */
    public UriPrefixingWebClient(final WebClient.Builder webClientBuilder, final String uri) {
        this(webClientBuilder, URI.create(uri));
    }

    private UriPrefixingWebClient(final WebClient.Builder webClientBuilder, final UriPrefixer prefixer) {
        this.webClient = notNull(webClientBuilder, "webClientBuilder").build();
        this.prefixer = notNull(prefixer, "prefixer");
    }

    /**
     * Create a request with the given path and HTTP method.
     *
     * @param path       the path for the HTTP request
     * @param httpMethod the HTTP method (e.g., GET, POST)
     * @return the WebClient.RequestHeadersSpec to customize and execute the request
     */
    public WebClient.RequestHeadersSpec<?> createRequest(final String path, final HttpMethod httpMethod) {
        final URI prefixedUri = prefixer.prefixUri(URI.create(path));
        return webClient.method(httpMethod).uri(prefixedUri);
    }
}
