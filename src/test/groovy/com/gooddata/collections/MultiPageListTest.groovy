/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.collections

import spock.lang.Specification

class MultiPageListTest extends Specification {

    final MultiPageList<Integer> singlePageList = new MultiPageList<>(
            new PageableList<>([11], null),
            {page -> null}
    )

    final MultiPageList<Integer> twoPageList = new MultiPageList<>(
            new PageableList<>([11], new Paging("nextpage2")),
            {page -> new PageableList<>([12], null)}
    )

    final PageableList delegate = Mock(PageableList)

    def "test size"() {
        expect:
        singlePageList.totalSize() == 1
        twoPageList.totalSize() == 2
    }

    def "test getCurrentPageItems"() {
        expect:
        singlePageList.getCurrentPageItems() == [11]
        twoPageList.getCurrentPageItems() == [11]
    }

    def "test collectAll"() {
        expect:
        singlePageList.collectAll() == [11]
        twoPageList.collectAll() == [11, 12]
    }

    def "test isEmpty"() {
        when:
        MultiPageList<Integer> empty = new MultiPageList<Integer>({page -> new PageableList<>()})

        then:
        empty.isEmpty()
        !singlePageList.isEmpty()
        !twoPageList.isEmpty()
    }

    def "test iterator"() {
        when:
        final Iterator<Integer> iterator = new MultiPageList<>(
                new PageableList<>([1], null),
                {page -> null}
        ).iterator()

        then:
        iterator.hasNext()
        iterator.next() == 1
        !iterator.hasNext()

        when:
        iterator.next()

        then:
        NoSuchElementException exc = thrown()
        exc.message ==~ /.*no more items left.*/
    }

    def "test iterator with PageProvider"() {
        when:
        final PageableList<Integer> lastPage = new PageableList<>([3], null)
        final PageableList<Integer> list = new MultiPageList<>(
                new PageableList<>([1, 2], new Paging("next")),
                {page -> lastPage}
        )

        then:
        final Iterator<Integer> iterator = list.iterator()
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
        exc.message ==~ /.*no more items left.*/
    }

    def "test stream"() {
        when:
        final PageableList<Integer> list = new MultiPageList<>(
                new PageableList<>([1, 2], null),
                {page -> null}
        )

        then:
        list.max() == 2
    }

    def "test stream with PageProvider"() {
        when:
        final PageableList<Integer> lastPage = new PageableList<>([3], null)
        final PageableList<Integer> list = new MultiPageList<>(
                new PageableList<>([1, 2], new Paging("next")),
                {page -> lastPage}
        )

        then:
        list.max() == 3
    }

    def "test infinite loop should be prevented"() {
        given:
        final PageableList<Integer> pageableList = new PageableList<>([1, 2], new Paging("next"))
        final PageableList<Integer> list = new MultiPageList<>(
                pageableList,
                {page -> pageableList}
        )

        when:
        list.forEach({})

        then:
        IllegalStateException exc = thrown()
        exc.message ==~ /.*page provider does not iterate properly, returns the same page.*/
    }

    def "should fail on misbehaving next page"() {
        given:
        final PageableList<Integer> pageableList = new PageableList<>([1, 2], new Paging("next"))
        final PageableList<Integer> misbehaving = Mock(PageableList)
        misbehaving.isEmpty() >> true
        misbehaving.getNextPage() >> new PageRequest()

        final PageableList<Integer> list = new MultiPageList<>(
                pageableList,
                {page -> misbehaving}
        )

        when:
        list.forEach({})

        then:
        IllegalStateException exc = thrown()
        exc.message ==~ /.*page has no results, yet claims there is next page.*/
    }

    def "test hasNext should only switch page once and only once"() {
        when:
        final PageableList<Integer> list = new MultiPageList<>(
                new PageableList<>([], new Paging("page2")),
                new SinglePageProvider(new PageableList<>([2], new Paging("page3")))
        )
        final Iterator<Integer> iterator = list.iterator()

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

    def "test delegate methods"() {
        when:
        final MultiPageList<String> list = new MultiPageList<String>(delegate, {page -> null})
        list.getNextPage()
        list.hasNextPage()
        list.getLinks()
        list.getPaging()
        list.size()
        list.contains("x")
        list.toArray()
        list.toArray(["x"])
        list.add("a")
        list.add(1, "a")
        list.remove(0)
        list.remove("b")
        list.clear()
        list.containsAll(["a", "b"])
        list.addAll(["a", "b"])
        list.addAll(1, ["a", "b"])
        list.removeAll(["a", "b"])
        list.retainAll(["a", "b"])
        list.set(0, "x")
        list.get(0)
        list.indexOf("x")
        list.lastIndexOf("x")
        list.listIterator()
        list.listIterator(1)
        list.subList(0, 1)

        then:
        1 * delegate.getNextPage()
        1 * delegate.hasNextPage()
        1 * delegate.getLinks()
        1 * delegate.getPaging()
        1 * delegate.size()
        1 * delegate.contains("x")
        1 * delegate.toArray()
        1 * delegate.toArray(["x"])
        1 * delegate.add("a")
        1 * delegate.add(1, "a")
        1 * delegate.remove(0)
        1 * delegate.remove("b")
        1 * delegate.clear()
        1 * delegate.containsAll(["a", "b"])
        1 * delegate.addAll(["a", "b"])
        1 * delegate.addAll(1, ["a", "b"])
        1 * delegate.removeAll(["a", "b"])
        1 * delegate.retainAll(["a", "b"])
        1 * delegate.set(0, "x")
        1 * delegate.get(0)
        1 * delegate.indexOf("x")
        1 * delegate.lastIndexOf("x")
        1 * delegate.listIterator()
        1 * delegate.listIterator(1)
        1 * delegate.subList(0, 1)
    }
}
