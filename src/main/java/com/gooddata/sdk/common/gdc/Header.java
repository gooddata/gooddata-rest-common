/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.gdc;

/**
 * Utility class containing common GoodData HTTP headers.
 */
public final class Header {

    /**
     * GoodData vendor specific header containing request identifier unique within GoodData.
     */
    public static final String GDC_REQUEST_ID = "X-GDC-REQUEST";

    /**
     * GoodData vendor specific header specifying API version to be used.
     */
    public static final String GDC_VERSION = "X-GDC-Version";

    /**
     * GoodData vendor specific header marking the called resource is in deprecation.
     */
    public static final String GDC_DEPRECATED = "X-GDC-DEPRECATED";

    private Header() {
        throw new AssertionError("This class is not supposed to be instantiated");
    }
}
