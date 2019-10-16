/*
 * Copyright (C) 2007-2019, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.collections

import spock.lang.Specification

import static java.util.stream.Collectors.toList

class PageBrowserTest extends Specification {

    final PageBrowser<Integer> singlePageList = new PageBrowser<>(
            new Page<>([11], null),
            {page -> null}
    )

    final PageBrowser<Integer> twoPageList = new PageBrowser<>(
            new Page<>([11], new Paging("nextpage2")),
            {page -> new Page<>([12], null)}
    )

    def "test size"() {
        when:
        PageBrowser<Integer> empty = new PageBrowser<Integer>({ page -> new Page<>()})

        then:
        empty.allItemsStream().count() == 0
        singlePageList.allItemsStream().count() == 1
        twoPageList.allItemsStream().count() == 2
    }

    def "test getCurrentPageItems"() {
        expect:
        singlePageList.getPageItems() == [11]
        twoPageList.getPageItems() == [11]
    }

    def "test collectAll"() {
        expect:
        singlePageList.allItemsStream().collect(toList()) == [11]
        twoPageList.allItemsStream().collect(toList()) == [11, 12]
    }

    def "test iterator"() {
        when:
        final Iterator<Integer> iterator = new PageBrowser<>(
                new Page<>([1], null),
                {page -> null}
        ).allItemsIterator()

        then:
        iterator.hasNext()
        iterator.next() == 1
        !iterator.hasNext()

        when:
        iterator.next()

        then:
        NoSuchElementException exc = thrown()
    }

    def "test iterator with PageProvider"() {
        when:
        final Page<Integer> lastPage = new Page<>([3], null)
        final Page<Integer> list = new PageBrowser<>(
                new Page<>([1, 2], new Paging("next")),
                {page -> lastPage}
        )

        then:
        final Iterator<Integer> iterator = list.allItemsIterator()
        iterator.hasNext()
        iterator.next() == 1
        iterator.hasNext()
        iterator.next() == 2
        iterator.hasNext()
        iterator.next() == 3

        !iterator.hasNext()

        when:
        iterator.next()

        then:
        NoSuchElementException exc = thrown()
    }

    def "test stream"() {
        when:
        final Page<Integer> list = new PageBrowser<>(
                new Page<>([1, 2], null),
                {page -> null}
        )

        then:
        list.allItemsStream().max(Comparator.naturalOrder()).get() == 2
    }

    def "test stream with PageProvider"() {
        when:
        final Page<Integer> lastPage = new Page<>([3], null)
        final Page<Integer> list = new PageBrowser<>(
                new Page<>([1, 2], new Paging("next")),
                {page -> lastPage}
        )

        then:
        list.allItemsStream().max(Comparator.naturalOrder()).get() == 3
    }

    def "test getAllItems"() {
        when:
        final Page<Integer> list = new PageBrowser<>(
                new Page<>([1, 2], null),
                {page -> null}
        )

        then:
        list.getAllItems().asList() == [1, 2]
    }

    def "test getAllItems with PageProvider"() {
        when:
        final Page<Integer> lastPage = new Page<>([3], null)
        final Page<Integer> list = new PageBrowser<>(
                new Page<>([1, 2], new Paging("next")),
                {page -> lastPage}
        )

        then:
        list.getAllItems().asList() == [1, 2, 3]
    }

    def "test infinite loop should be prevented"() {
        given:
        final Page<Integer> pageableList = new Page<>([1, 2], new Paging("next"))
        final Page<Integer> list = new PageBrowser<>(
                pageableList,
                {page -> pageableList}
        )

        when:
        list.allItemsStream().forEach({})

        then:
        IllegalStateException exc = thrown()
        exc.message ==~ /.*page provider does not iterate properly, returns the same page.*/
    }

    def "should fail on misbehaving next page"() {
        given:
        final Page<Integer> pageableList = new Page<>([1, 2], new Paging("next"))
        final Page<Integer> misbehaving = Mock(Page)
        misbehaving.getPageItems() >> Mock(List)
        misbehaving.getPageItems().isEmpty() >> true
        misbehaving.getNextPage() >> new CustomPageRequest()

        final Page<Integer> list = new PageBrowser<>(
                pageableList,
                {page -> misbehaving}
        )

        when:
        list.allItemsStream().forEach({})

        then:
        IllegalStateException exc = thrown()
        exc.message ==~ /.*page has no results, yet claims there is next page.*/
    }

    def "test hasNext should only switch page once and only once"() {
        when:
        final Page<Integer> list = new PageBrowser<>(
                new Page<>([], new Paging("page2")),
                new SinglePageProvider(new Page<>([2], new Paging("page3")))
        )
        final Iterator<Integer> iterator = list.allItemsIterator()

        then:
        iterator.hasNext()
        iterator.hasNext()

        iterator.next() == 2

        when:
        iterator.hasNext()

        then:
        IllegalStateException exc = thrown()
        exc.message ==~ /.*second page has been requested.*/
    }
}
