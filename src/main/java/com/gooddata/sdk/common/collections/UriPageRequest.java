/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.collections;

import com.gooddata.sdk.common.util.GoodDataToStringBuilder;
import com.gooddata.sdk.common.util.MutableUri;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map.Entry;

import static com.gooddata.sdk.common.util.Validate.notNull;

/**
 * {@link PageRequest} implementation wrapping next page link from REST API.
 */
class UriPageRequest implements PageRequest {

    private final UriComponents pageUri;

    /**
     * Creates new instance with defined page URI.
     *
     * @param pageUri page URI
     */
    public UriPageRequest(final String pageUri) {
        this.pageUri = UriComponentsBuilder.fromUriString(UriUtils.decode(notNull(pageUri, "pageUri"), "UTF-8")).build();
    }

    /**
     * This is effectively no-op. Returns internal URI provided by REST API.
     *
     * @param mutableUri not used internally, can be null
     * @return next page URI provided by REST API
     */
    @Override
    public URI getPageUri(final MutableUri mutableUri) {
        return pageUri.toUri();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Note that by using this method you might end up with URI that will be different from the one returned by
     * {@link #getPageUri(MutableUri)}. Method only copies query parameters and does not care about
     * URI path.
     */
    @Override
    public MutableUri updateWithPageParams(final MutableUri mutableUri) {
        notNull(mutableUri, "mutableUri");
        for (Entry<String, List<String>> entry : pageUri.getQueryParams().entrySet()) {
            mutableUri.replaceQueryParam(entry.getKey(), entry.getValue().toArray());
        }
        return mutableUri;
    }

    @Override
    public String toString() {
        return GoodDataToStringBuilder.defaultToString(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UriPageRequest uriPage = (UriPageRequest) o;

        return pageUri != null ? pageUri.equals(uriPage.pageUri) : uriPage.pageUri == null;
    }

    @Override
    public int hashCode() {
        return pageUri != null ? pageUri.hashCode() : 0;
    }
}
