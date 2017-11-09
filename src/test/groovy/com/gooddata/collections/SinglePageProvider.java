/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.collections;

import java.util.function.Function;

/**
 * This provider will only provide one page, but will throw an exception when asked to provide another
 */
class SinglePageProvider implements Function<Page, PageableList<Integer>> {
    int count = 0;
    final PageableList<Integer> page;

    SinglePageProvider(final PageableList<Integer> page) {
        this.page = page;
    }

    @Override
    public PageableList<Integer> apply(final Page page) {
        count++;
        if(count > 1) {
            throw new IllegalStateException("second page has been requested");
        }

        return this.page;
    }
}

