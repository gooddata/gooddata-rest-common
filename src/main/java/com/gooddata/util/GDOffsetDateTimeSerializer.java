/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Serializes JSR 310 {@link OffsetDateTime} fields to the GoodDate date time format in the UTC timezone ({@value DATE_TIME_PATTERN}).
 */
public class GDOffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withZone(ZoneOffset.UTC);

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider __) throws IOException {
        gen.writeString(FORMATTER.format(value));
    }
}
