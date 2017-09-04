/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rest.common;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Factory for ClientHttpRequest objects.
 * Sets default URI prefix for Spring REST client which by default requires absolute URI.
 * It also allows to specify default hostname and port.
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
    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory,
                                                final URI uriPrefix) {
        this(factory, new UriPrefixer(uriPrefix));
    }

    /**
     * Create an instance that will wrap the given {@link org.springframework.http.client.ClientHttpRequestFactory}
     * and use the given hostname, port, and protocol for all HTTP requests
     *
     * @param factory  the factory to be wrapped
     * @param hostname the hostname for all HTTP requests
     * @param port     the port for all HTTP requests
     * @param protocol the protocol for all HTTP requests
     */
    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory,
                                         final String hostname,
                                         final int port,
                                         final String protocol) {
        this(factory, UriComponentsBuilder.newInstance().scheme(protocol).host(hostname).port(port).build().toUri());
    }

    private UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory, final UriPrefixer prefixer) {
        this.wrapped = notNull(factory, "factory");
        this.prefixer =  notNull(prefixer, "prefixer");
    }

    @Override
    public ClientHttpRequest createRequest(final URI uri, final HttpMethod httpMethod) throws IOException {
        final URI merged = prefixer.mergeUris(uri);
        return wrapped.createRequest(merged, httpMethod);
    }

}
