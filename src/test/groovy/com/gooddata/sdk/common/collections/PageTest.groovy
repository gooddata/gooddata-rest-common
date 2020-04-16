/*
 * Copyright (C) 2007-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.collections

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.gooddata.sdk.common.util.ResourceUtils.readObjectFromResource
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals
import static net.javacrumbs.jsonunit.core.util.ResourceUtils.resource
import static spock.util.matcher.HamcrestSupport.that

class PageTest extends Specification {

    def "test collection empty"() {
        when:
        Page<Integer> collection = new Page<>()

        then:
        collection.getPageItems().isEmpty()
        collection.nextPage == null
    }

    def "test collection"() {
        when:
        Page<Integer> collection = new Page<>([1, 2, 3], null)

        then:
        collection.getPageItems().size() == 3
        collection.nextPage == null
        collection.pageItems == [1, 2, 3]
    }

    def "test collection with paging"() {
        when:
        Page<Integer> collection = new Page<>([1, 2, 3], new Paging('1', 'next'))

        then:
        collection.getPageItems().size() == 3
        collection.nextPage?.getPageUri(null)?.toString() == 'next'
    }

    def "should verify equals"() {
        expect:
        EqualsVerifier.forClass(Page).usingGetClass().withNonnullFields('items').verify()
    }

    def "should deserialize"() {
        when:
        Elements elements = readObjectFromResource('/collections/elements.json', Elements)

        then:
        elements
        elements.links == ['self': 'self']
        elements.paging?.nextUri == 'next'
        elements.hasNextPage()
        elements.getPageItems() == ['first', 'second']
    }

    def "should serialize"() {
        expect:
        that new Elements(['first', 'second'], new Paging('next'), ['self': 'self']),
                jsonEquals(resource('collections/elements.json'))
    }

}
