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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserialize JSR 310 {@link ZonedDateTime} fields from the ISO date time format in the UTC timezone ({@value ISOZonedDateTimeDeserializer#DATE_TIME_PATTERN}).
 */
public class ISOZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    /**
     * Used Offset 'X' will output 'Z' when the offset to be output would be zero.
     * <p>
     * Note that this pattern enable:
     * - single month/day digit date without 0 padding (1999-2-3)
     * - double digit date with 0 padding (1999-02-03)
     * - normal two-digit dates (1999-11-26)
     *
     * @see DateTimeFormatter
     */
    private static final String DATE_TIME_PATTERN = "yyyy-M-d'T'HH:mm:ss.SSSX";

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withZone(ZoneOffset.UTC);

    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext __) throws IOException {
        final JsonNode root = jp.readValueAsTree();
        if (root == null || root.isNull()) {
            return null;
        }
        return ZonedDateTime.parse(root.textValue(), FORMATTER);
    }
}
