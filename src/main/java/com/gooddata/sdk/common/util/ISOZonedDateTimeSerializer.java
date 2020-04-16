/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.gooddata.sdk.common.util.ISOZonedDateTime.FORMATTER;

/**
 * Serializes JSR 310 {@link ZonedDateTime} fields to the ISO date time format in the UTC timezone ({@value ISOZonedDateTime#DATE_TIME_PATTERN}).
 */
public class ISOZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider __) throws IOException {
        gen.writeString(FORMATTER.format(value));
    }
}
