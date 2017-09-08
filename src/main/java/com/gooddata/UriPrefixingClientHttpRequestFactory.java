/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static com.gooddata.util.Validate.notNull;

/**
 * Factory for ClientHttpRequest objects.
 * Sets default URI prefix for Spring REST client which by default requires absolute URI.
 * UriPrefixingAsyncClientHttpRequestFactory allows you to specify default hostname and port.
 */
public class UriPrefixingClientHttpRequestFactory implements ClientHttpRequestFactory {

    private final ClientHttpRequestFactory wrapped;
    private final UriPrefixer prefixer;

    /**
     * Create an instance that will wrap the given {@link org.springframework.http.client.ClientHttpRequestFactory}
     * and use the given URI for setting hostname and port of all HTTP requests
     *
     * @param factory   the factory to be wrapped
     * @param uriPrefix the URI for setting hostname and port of all HTTP requests
     */
    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory, final URI uriPrefix) {
        this(factory, new UriPrefixer(uriPrefix));
    }

    /**
     * Create an instance that will wrap the given {@link org.springframework.http.client.ClientHttpRequestFactory}
     * and use the given hostname, port, and protocol for all HTTP requests
     *
     * @param factory  the factory to be wrapped
     * @param uri the URI for setting hostname and port of all HTTP requests
     */
    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory, final String uri) {
        this(factory, URI.create(uri));
    }

    /**
     * Create an instance that will wrap the given {@link org.springframework.http.client.ClientHttpRequestFactory}
     * and use the given protocol, hostname and port for all HTTP requests
     *
     * @param factory  the factory to be wrapped
     * @param protocol the protocol for all HTTP requests
     * @param hostname the hostname for all HTTP requests
     * @param port     the port for all HTTP requests
     */
    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory,
                                                final String protocol,
                                                final String hostname,
                                                final int port) {
        this(factory, UriComponentsBuilder.newInstance().scheme(protocol).host(hostname).port(port).build().toUri());
    }

    private UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory, final UriPrefixer prefixer) {
        this.wrapped = notNull(factory, "factory");
        this.prefixer =  notNull(prefixer, "prefixer");
    }

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        final URI merged = prefixer.mergeUris(uri);
        return wrapped.createRequest(merged, httpMethod);
    }

}