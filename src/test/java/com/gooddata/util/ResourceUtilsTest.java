/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import org.testng.annotations.Test;

import static com.gooddata.util.ResourceUtils.readStringFromResource;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class ResourceUtilsTest {

    @Test
    public void shouldReadStringFromResource() throws Exception {
        assertThat(readStringFromResource("/test.txt"), is("foo"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.txt.*")
    public void shouldFailOnNonExistingResource() throws Exception {
        readStringFromResource("/foo.txt");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnNullResource() throws Exception {
        readStringFromResource(null);
    }
}