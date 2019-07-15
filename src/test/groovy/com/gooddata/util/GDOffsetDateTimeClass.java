/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

import java.time.OffsetDateTime;

class GDOffsetDateTimeClass {

    @GDOffsetDateTime
    private final OffsetDateTime date;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GDOffsetDateTimeClass(final OffsetDateTime date) {
        this.date = date;
    }

    public OffsetDateTime getDate() {
        return date;
    }
}
