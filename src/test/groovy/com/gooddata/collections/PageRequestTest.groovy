/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.collections

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.collections.PageRequest.DEFAULT_LIMIT

class PageRequestTest extends Specification {

    def "should get and set values"() {
        when:
        PageRequest pageRequest = new PageRequest('foo', 123)

        then:
        pageRequest.offset == 'foo'
        pageRequest.limit == 123

        when:
        pageRequest.offset = 'bar'
        pageRequest.limit = 987

        then:
        pageRequest.offset == 'bar'
        pageRequest.limit == 987
    }

    @Unroll
    def "should getPageUri with #type"() {
        when:
        URI pageUri = pageRequest.getPageUri(UriComponentsBuilder.fromUriString('test_uri'))

        then:
        pageUri?.toString() == expected

        where:
        type             | pageRequest               || expected
        'default values' | new PageRequest()         || 'test_uri?limit=100'
        'int offset'     | new PageRequest(12, 10)   || 'test_uri?offset=12&limit=10'
        'string offset'  | new PageRequest('17', 10) || 'test_uri?offset=17&limit=10'
    }

    @Unroll
    def "should updateWithPageParams with #type offset"() {
        given:
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString('test_uri/{test}')
        PageRequest pageRequest = new PageRequest(offset, 10)

        when:
        UriComponentsBuilder updatedBuilder = pageRequest.updateWithPageParams(uriBuilder)
        String pageUri = updatedBuilder.build().toUriString()

        then:
        pageUri == 'test_uri/{test}?offset=12&limit=10'

        and: "is idempotent operation"
        pageRequest.updateWithPageParams(updatedBuilder).build().toString() == pageUri

        where:
        type     | offset
        'int'    | 12
        'string' | '12'
    }

    def "should get sanitized limit"() {
        expect:
        new PageRequest(limit).sanitizedLimit == sanitizedLimit

        where:
        limit || sanitizedLimit
        -2    || DEFAULT_LIMIT
        0     || DEFAULT_LIMIT
        100   || 100
    }

    def "should get max sanitized limit"() {
        expect:
        new PageRequest(100).getSanitizedLimit(10) == 10
    }

    def "should have correct toString"() {
        expect:
        new PageRequest(1, 2).toString() == 'PageRequest[offset=1,limit=2]'
    }

    def "should verify equals"() {
        expect:
        EqualsVerifier.forClass(PageRequest)
                .withRedefinedSubclass(PageRequestChild)
                .suppress(Warning.NONFINAL_FIELDS) // this is java bean
                .verify()
    }

}
