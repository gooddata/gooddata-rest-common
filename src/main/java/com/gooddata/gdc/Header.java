/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.gdc;

/**
 * Utility class containing common GoodData HTTP headers.
 */
public final class Header {

    public static final String GDC_REQUEST_ID = "X-GDC-REQUEST";

    private Header() {
        throw new AssertionError("This class is not supposed to be instantiated");
    }
}
