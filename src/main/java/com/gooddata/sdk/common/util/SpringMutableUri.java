/*
 * Copyright (C) 2007-2020, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static com.gooddata.sdk.common.util.Validate.notNull;

/**
 * Spring framework based implementation of {@link MutableUri}. Works as wrapper for spring's {@link UriComponentsBuilder}.
 * To use this class you need to explicitly declare spring-web dependency.
 */
public class SpringMutableUri implements MutableUri {

    private final UriComponentsBuilder builder;

    /**
     * New instance from given uri
     * @param uri uri to wrap
     */
    public SpringMutableUri(final URI uri) {
        this(UriComponentsBuilder.fromUri(uri));
    }

    /**
     * New instance from given uri string
     * @param uriString uri to wrap
     */
    public SpringMutableUri(final String uriString) {
        this(UriComponentsBuilder.fromUriString(uriString));
    }

    private SpringMutableUri(final UriComponentsBuilder builder) {
        this.builder = notNull(builder, "builder");
    }

    @Override
    public MutableUri copy() {
        final UriComponentsBuilder copy = UriComponentsBuilder.fromUriString(builder.build().toUriString());
        return new SpringMutableUri(copy);
    }

    @Override
    public void replaceQueryParam(final String name, final Object... values) {
        builder.replaceQueryParam(name, values);
    }

    @Override
    public URI toUri() {
        return getUriComponents().toUri();
    }

    @Override
    public String toUriString() {
        return getUriComponents().toUriString();
    }

    /**
     * Uri components representing the current state. The difference from {@link #toUri()} and {@link #toUriString()} is
     * that {@link UriComponents} don't have possibly templated uri resolved.
     * @return uri components
     */
    public UriComponents getUriComponents() {
        return builder.build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpringMutableUri that = (SpringMutableUri) o;
        return Objects.equals(builder, that.builder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(builder);
    }
}
