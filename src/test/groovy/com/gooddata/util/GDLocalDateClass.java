/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDate;

public class GDLocalDateClass {

    @GDLocalDate
    private final LocalDate date;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GDLocalDateClass(final LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
