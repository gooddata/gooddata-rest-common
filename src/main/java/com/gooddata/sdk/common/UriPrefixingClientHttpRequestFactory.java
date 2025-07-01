/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static com.gooddata.sdk.common.util.Validate.notNull;


// AsyncClientHttpRequestFactory and createAsyncRequest was removed in Spring 5.0/6.0 
// so this class is no longer used in the SDK. It is kept here for backward compatibility

/**
 *  now for calling use  RestTemplate with  UriPrefixingClientHttpRequestFactory
 * 
 *  import org.springframework.web.client.RestTemplate;
    import java.util.concurrent.CompletableFuture;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;

    public class AsyncRestTemplateExample {

        private final RestTemplate restTemplate;
        private final ExecutorService executor = Executors.newFixedThreadPool(8);

        public AsyncRestTemplateExample(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public CompletableFuture<String> getAsync(String url) {
            return CompletableFuture.supplyAsync(() ->
                    restTemplate.getForObject(url, String.class), executor);
        }
    }


    and used:

    // Create a RestTemplate with a URI prefixing factory
    RestTemplate restTemplate = new RestTemplate(
        new UriPrefixingClientHttpRequestFactory(
            * e.g., HttpComponentsClientHttpRequestFactory *,
            "https://my-api.example.com"
        )
    );

    // Create an instance of the asynchronous RestTemplate wrapper
    AsyncRestTemplateExample asyncExample = new AsyncRestTemplateExample(restTemplate);

    // Perform an asynchronous GET request and print the response when ready
    asyncExample.getAsync("/my/resource")
        .thenAccept(response -> System.out.println("Response: " + response));

 * 
 * 
 * 
 */


 
public class UriPrefixingClientHttpRequestFactory implements ClientHttpRequestFactory {

    private final ClientHttpRequestFactory wrapped;
    private final UriPrefixer prefixer;

    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory, final URI uriPrefix) {
        this(factory, new UriPrefixer(uriPrefix));
    }

    public UriPrefixingClientHttpRequestFactory(final ClientHttpRequestFactory factory, final String uri) {
        this(factory, URI.create(uri));
    }

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
    public ClientHttpRequest createRequest(final URI uri, final HttpMethod httpMethod) throws IOException {
        final URI merged = prefixer.mergeUris(uri);
        return wrapped.createRequest(merged, httpMethod);
    }
}
