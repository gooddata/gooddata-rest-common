/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util;

import org.testng.annotations.Test;

import java.util.List;

import static com.gooddata.util.Validate.isTrue;
import static com.gooddata.util.Validate.noNullElements;
import static com.gooddata.util.Validate.notEmpty;
import static com.gooddata.util.Validate.notNull;
import static com.gooddata.util.Validate.notNullState;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class ValidateTest {

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnNull() throws Exception {
        notNull(null, "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnEmpty() throws Exception {
        notEmpty("", "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnBlank() throws Exception {
        notEmpty(" ", "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnNullCollection() throws Exception {
        final List<Object> list = null;
        notEmpty(list, "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnEmptyCollection() throws Exception {
        notEmpty(emptyList(), "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnNullElementsForNullCollection() throws Exception {
        final List<Object> list = null;
        noNullElements(list, "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnNullElementsForCollection() throws Exception {
        noNullElements(singletonList(null), "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnNullElementsForNullArray() throws Exception {
        noNullElements(new Object[]{null}, "foo");
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnNullState() throws Exception {
        notNullState(null, "foo");
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".*foo.*")
    public void shouldFailOnFalse() throws Exception {
        isTrue(false, "foo");
    }
}