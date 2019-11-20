/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = ISOZonedDateTimeSerializer.class)
@JsonDeserialize(using = ISOZonedDateTimeDeserializer.class)
@Documented
public @interface ISOZonedDateTime {

    /**
     * Used Offset 'X' will output 'Z' when the offset to be output would be zero.
     * @see DateTimeFormatter
     */
    String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    /**
     * Formatter compatible with original joda-time {@link org.joda.time.format.ISODateTimeFormat}
     */
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withZone(ZoneOffset.UTC);
}
