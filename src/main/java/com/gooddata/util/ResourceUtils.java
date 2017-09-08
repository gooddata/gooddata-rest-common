/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import static com.gooddata.util.Validate.notEmpty;
import static com.gooddata.util.Validate.notNull;
import static java.lang.String.format;

import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T readObjectFromResource(final String resourcePath, final Class<T> objectClass) {
        return readObjectFromResource(ResourceUtils.class, resourcePath, objectClass);
    }

    public static <T> T readObjectFromResource(final Class testClass, final String resourcePath, final Class<T> objectClass) {
        notNull(objectClass, "objectClass");

        try {
            return OBJECT_MAPPER.readValue(readFromResource(resourcePath, testClass), objectClass);
        } catch (IOException e) {
            throw new IllegalStateException(format("Cannot read class %s from resource %s", objectClass, resourcePath), e);
        }
    }

    public static String readStringFromResource(final String resourcePath) {
        try {
            return IOUtils.toString(readFromResource(resourcePath),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot read from resource: " + resourcePath, e);
        }
    }

    public static InputStream readFromResource(final String resourcePath) {
        final Class<?> clazz = ResourceUtils.class;
        return readFromResource(resourcePath, clazz);
    }

    public static InputStream readFromResource(final String resourcePath, final Class<?> testClass) {
        notEmpty(resourcePath, "resourcePath");
        notNull(testClass, "testClass");

        final InputStream stream = testClass.getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }
        return stream;
    }
}
