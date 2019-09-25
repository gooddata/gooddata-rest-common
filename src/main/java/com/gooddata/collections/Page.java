/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.collections;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gooddata.util.Validate.notNull;
import static java.util.Collections.unmodifiableList;

/**
 * Wrapper over pageable GDC list.
 *
 * @param <E> type of collection elements
 */
public class Page<E> {

    static final String ITEMS_NODE = "items";
    static final String LINKS_NODE = "links";
    static final String PAGING_NODE = "paging";

    private final List<E> items;
    private final Paging paging;
    private final Map<String, String> links;

    /**
     * Creates empty page with no next page.
     */
    public Page() {
        this(Collections.emptyList(), null);
    }

    /**
     * Creates page wrapping provided items and next page.
     *
     * @param items  to be wrapped
     * @param paging page description, might be <code>null</code>
     */
    public Page(final List<E> items, final Paging paging) {
        this(items, paging, null);
    }

    /**
     * Creates page wrapping provided items, next page and links.
     *
     * @param items  to be wrapped
     * @param paging page description, might be <code>null</code>
     * @param links  links, might be <code>null</code>
     */
    public Page(final List<E> items, final Paging paging, final Map<String, String> links) {
        this.items = notNull(items, "items");
        this.paging = paging;
        this.links = links;
    }

    /**
     * Returns items of this and only this page.
     *
     * @return this page items
     */
    public List<E> getPageItems() {
        return unmodifiableList(items);
    }

    /**
     * Returns description of the next page.
     *
     * @return next page, might be <code>null</code>
     */
    public PageRequest getNextPage() {
        return paging == null ? null : paging.getNext();
    }

    /**
     * Signals whether there are more subsequent pages or the last page has been reached
     *
     * @return true if there are more results to come
     */
    public boolean hasNextPage() {
        return getNextPage() != null;
    }

    /**
     * Returns map of links.
     *
     * @return map of links, might be <code>null</code>
     */
    public Map<String, String> getLinks() {
        return links;
    }

    /**
     * Returns description of this page.
     *
     * @return page description, might be <code>null</code>
     */
    public Paging getPaging() {
        return paging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> that = (Page<?>) o;
        return items.equals(that.items) &&
                Objects.equals(paging, that.paging) &&
                Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, paging, links);
    }
}
