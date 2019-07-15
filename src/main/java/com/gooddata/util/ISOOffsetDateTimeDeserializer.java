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

import static com.gooddata.util.ISOOffsetDateTimeSerializer.FORMATTER;

/**
 * Deserializes JSR 310 {@link OffsetDateTime} fields from the ISO date time format in the UTC timezone (TODO).
 */
public class ISOOffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext __) throws IOException {
        final JsonNode root = jp.readValueAsTree();
        if (root == null || root.isNull()) {
            return null;
        }
        return OffsetDateTime.parse(root.textValue(), FORMATTER);
    }
}
