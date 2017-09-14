/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.collections;

import com.gooddata.util.ResourceUtils;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.gooddata.util.ResourceUtils.readObjectFromResource;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static net.javacrumbs.jsonunit.core.util.ResourceUtils.resource;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class PageableListTest {

    @Test
    public void testCollectionEmpty() {
        final PageableList<Integer> collection = new PageableList<>();
        assertThat(collection, notNullValue());
        assertThat(collection, empty());
        assertThat(collection.getNextPage(), nullValue());
    }

    @Test
    public void testCollection() {
        final PageableList<Integer> collection = new PageableList<>(asList(1, 2, 3), null);
        assertThat(collection, notNullValue());
        assertThat(collection, hasSize(3));
        assertThat(collection.getNextPage(), nullValue());
        assertThat(collection.getCurrentPageItems(), is(asList(1, 2, 3)));
        assertThat(collection.collectAll(), is(asList(1, 2, 3)));
    }

    @Test
    public void testCollectionWithPaging() {
        final PageableList<Integer> collection = new PageableList<>(asList(1, 2, 3), new Paging("1", "next"));
        assertThat(collection, notNullValue());
        assertThat(collection, hasSize(3));
        assertThat(collection.getNextPage(), notNullValue());
        assertThat(collection.getNextPage().getPageUri(null).toString(), is("next"));
    }

    @Test
    public void testEquals() {
        assertThat(new PageableList<>(), is(new PageableList<>()));
        assertThat(new PageableList<>(singletonList(1), null), is(not(new PageableList<>())));
    }


    @Test
    public void testHashCode() {
        assertThat(new PageableList<>().hashCode(), is(new PageableList<>().hashCode()));
        assertThat(new PageableList<>(singletonList(1), null).hashCode(), is(not(new PageableList<>().hashCode())));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        final Elements elements = readObjectFromResource("/collections/elements.json", Elements.class);
        assertThat(elements, is(notNullValue()));

        assertThat(elements.getLinks(), hasEntry("self", "self"));

        assertThat(elements.getPaging(), is(notNullValue()));
        assertThat(elements.getPaging().getNextUri(), is("next"));
        assertThat(elements.hasNextPage(), is(true));

        assertThat(elements, hasItems("first", "second"));
    }

    @Test
    public void shouldSerialize() throws Exception {
        final Elements elements = new Elements(asList("first", "second"), new Paging("next"), singletonMap("self", "self"));
        assertThat(elements, jsonEquals(resource("collections/elements.json")));
    }
}
