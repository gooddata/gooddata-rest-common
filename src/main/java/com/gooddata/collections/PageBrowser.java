/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 * {@link Page} extended with support for easy iteration/streaming over multiple pages, lazily loading of pages using
 * specified {@code pageProvider}.
 */
public class PageBrowser<T> extends Page<T> {

    private final Function<PageRequest, Page<T>> pageProvider;

    /**
     * To list all pages, starting with the very first one.
     *
     * @param pageProvider provider used to load pages
     */
    public PageBrowser(final Function<PageRequest, Page<T>> pageProvider) {
        this(new CustomPageRequest(), pageProvider);
    }

    /**
     * To list all pages, starting with requested start page.
     *
     * @param startPageRequest page to start with
     * @param pageProvider     provider used to load pages
     */
    public PageBrowser(final PageRequest startPageRequest, final Function<PageRequest, Page<T>> pageProvider) {
        this(requireNonNull(pageProvider, "pageProvider can't be null").apply(startPageRequest), pageProvider);
    }

    PageBrowser(final Page<T> currentPage, final Function<PageRequest, Page<T>> pageProvider) {
        super(requireNonNull(currentPage, "currentPage can't be null").getPageItems(),
                currentPage.getPaging(),
                currentPage.getLinks());
        this.pageProvider = requireNonNull(pageProvider, "pageProvider can't be null");
    }

    /**
     * Returns stream of items starting from this page and going through all subsequent pages.
     *
     * @return stream of all items of all pages in this collection
     */
    public Stream<T> allItemsStream() {
        final Iterable<Page<T>> iterable = () -> new PageIterator<>(pageProvider, getNextPage());
        return Stream.concat(getPageItems().stream(),
                StreamSupport.stream(iterable.spliterator(), false)
                        .map(Page::getPageItems)
                        .flatMap(Collection::stream));
    }

    /**
     * Returns iterator over items starting from this page and going through all subsequent pages.
     *
     * @return iterator over all items of all pages in this collection
     */
    public Iterator<T> allItemsIterator() {
        return allItemsStream().iterator();
    }

    /**
     * Returns all items starting from this page and going through all subsequent pages.
     *
     * @return all items of all pages in this collection
     */
    public Iterable<T> getAllItems() {
        return this::allItemsIterator;
    }

    private static final class PageIterator<T> implements Iterator<Page<T>> {

        private PageRequest pageRequest;
        private final Function<PageRequest, Page<T>> pageProvider;

        PageIterator(final Function<PageRequest, Page<T>> pageProvider, final PageRequest pageRequest) {
            this.pageProvider = pageProvider;
            this.pageRequest = pageRequest;
        }

        @Override
        public boolean hasNext() {
            return pageRequest != null;
        }

        @Override
        public Page<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            final Page<T> next = pageProvider.apply(pageRequest);
            if (pageRequest.equals(next.getNextPage())) {
                throw new IllegalStateException("page provider does not iterate properly, returns the same page");
            }

            if (next.getPageItems().isEmpty() && next.getNextPage() != null) {
                throw new IllegalStateException("page has no results, yet claims there is next page");
            }

            pageRequest = next.getNextPage();
            return next;
        }
    }

}
