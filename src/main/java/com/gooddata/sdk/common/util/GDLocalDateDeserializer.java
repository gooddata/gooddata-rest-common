/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deserialize to JSR 310 {@link LocalDate} fields from the GoodData date time format ({@value GDLocalDateDeserializer#DESERIALIZE_PATTERN}).
 */
public class GDLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    /**
     * Note that this pattern enable:
     * - single month/day digit date without 0 padding (1999-2-3)
     * - double digit date with 0 padding (1999-02-03)
     * - normal two-digit dates (1999-11-26)
     */
    private static final String DESERIALIZE_PATTERN = "yyyy-M-d";
    
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DESERIALIZE_PATTERN);

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        final JsonNode root = jp.readValueAsTree();
        if (root == null || root.isNull()) {
            return null;
        }
        return LocalDate.parse(root.textValue(), FORMATTER);
    }
}
