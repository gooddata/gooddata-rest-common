/*
 * Copyright (C) 2007-2019, GoodData(R) Corporation. All rights reserved.
 */

package com.gooddata.collections;

import java.util.function.Function;

/**
 * This provider will only provide one page, but will throw an exception when asked to provide another
 */
class SinglePageProvider implements Function<PageRequest, Page<Integer>> {
    int count = 0;
    final Page<Integer> page;

    SinglePageProvider(final Page<Integer> page) {
        this.page = page;
    }

    @Override
    public Page<Integer> apply(final PageRequest pageRequest) {
        count++;
        if(count > 1) {
            throw new IllegalStateException("second page has been requested");
        }

        return this.page;
    }
}

