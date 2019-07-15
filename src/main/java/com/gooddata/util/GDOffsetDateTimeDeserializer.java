/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.gooddata.util.GDOffsetDateTimeSerializer.DATE_TIME_PATTERN;

/**
 * Deserializes JSR 310 {@link OffsetDateTime} fields from the GoodData date time format in the UTC timezone ({@value GDOffsetDateTimeSerializer#DATE_TIME_PATTERN}).
 */
public class GDOffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    /**
     * Specifically for Java 8 it's necessary to parse date with {@link ZonedDateTime} because of https://bugs.openjdk.java.net/browse/JDK-8074406
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withZone(ZoneOffset.UTC);

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext __) throws IOException {
        final JsonNode root = jp.readValueAsTree();
        if (root == null || root.isNull()) {
            return null;
        }
        return ZonedDateTime.parse(root.textValue(), DATE_TIME_FORMATTER).toOffsetDateTime();
    }
}
