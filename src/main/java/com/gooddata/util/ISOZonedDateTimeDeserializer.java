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
import java.time.ZonedDateTime;

import static com.gooddata.util.ISOZonedDateTimeSerializer.FORMATTER;

/**
 * Deserialize JSR 310 {@link ZonedDateTime} fields from the ISO date time format in the UTC timezone ({@value ISOZonedDateTimeSerializer#DATE_TIME_PATTERN}).
 */
public class ISOZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext __) throws IOException {
        final JsonNode root = jp.readValueAsTree();
        if (root == null || root.isNull()) {
            return null;
        }
        return ZonedDateTime.parse(root.textValue(), FORMATTER);
    }
}
