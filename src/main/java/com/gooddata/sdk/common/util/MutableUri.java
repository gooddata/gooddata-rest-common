/*
 * Copyright (C) 2007-2020, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util;

import java.net.URI;

/**
 * Mutable form of URI. Provides simple abstraction to separate the URI mutation logic. The default implemntation
 * {@link SpringMutableUri} uses spring-web library which is otherwise not needed when using gooddata-rest-common ie.
 * only for model classes.
 */
public interface MutableUri {

    /**
     * Copies this instance.
     * @return copy this instance
     */
    MutableUri copy();

    /**
     * Replaces the query param of given name with given values.
     *
     * @param name query param name
     * @param values query param values
     */
    void replaceQueryParam(final String name, final Object... values);

    /**
     * URI created from current state of this instance.
     * @return uri
     */
    URI toUri();

    /**
     * String representation of URI created from current state of this instance.
     * @return uri as string
     */
    String toUriString();
}
