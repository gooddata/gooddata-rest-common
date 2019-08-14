/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.ZonedDateTime;

class GDZonedDateTimeClass {

    @GDZonedDateTime
    private final ZonedDateTime date;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GDZonedDateTimeClass(final ZonedDateTime date) {
        this.date = date;
    }

    public ZonedDateTime getDate() {
        return date;
    }
}
