/*
 * Copyright (C) 2007-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.collections

import com.gooddata.sdk.common.util.MutableUri
import com.gooddata.sdk.common.util.SpringMutableUri
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification
import spock.lang.Unroll

import static CustomPageRequest.DEFAULT_LIMIT

class CustomPageRequestTest extends Specification {

    def "should get and set values"() {
        when:
        CustomPageRequest pageRequest = new CustomPageRequest('foo', 123)

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
        URI pageUri = pageRequest.getPageUri(new SpringMutableUri('test_uri'))

        then:
        pageUri?.toString() == expected

        where:
        type             | pageRequest                     || expected
        'default values' | new CustomPageRequest()         || 'test_uri?limit=100'
        'int offset'     | new CustomPageRequest(12, 10)   || 'test_uri?offset=12&limit=10'
        'string offset'  | new CustomPageRequest('17', 10) || 'test_uri?offset=17&limit=10'
    }

    @Unroll
    def "should updateWithPageParams with #type offset"() {
        given:
        MutableUri mutableUri = new SpringMutableUri('test_uri/{test}')
        CustomPageRequest pageRequest = new CustomPageRequest(offset, 10)

        when:
        MutableUri updatedMutableUri = pageRequest.updateWithPageParams(mutableUri)
        String pageUri = updatedMutableUri.toUriString()

        then:
        pageUri == 'test_uri/{test}?offset=12&limit=10'

        and: "is idempotent operation"
        pageRequest.updateWithPageParams(updatedMutableUri).toUriString() == pageUri

        where:
        type     | offset
        'int'    | 12
        'string' | '12'
    }

    def "should get sanitized limit"() {
        expect:
        new CustomPageRequest(limit).sanitizedLimit == sanitizedLimit

        where:
        limit || sanitizedLimit
        -2    || DEFAULT_LIMIT
        0     || DEFAULT_LIMIT
        100   || 100
    }

    def "should get max sanitized limit"() {
        expect:
        new CustomPageRequest(100).getSanitizedLimit(10) == 10
    }

    def "should have correct toString"() {
        expect:
        new CustomPageRequest(1, 2).toString() == 'CustomPageRequest[offset=1,limit=2]'
    }

    def "should verify equals"() {
        expect:
        EqualsVerifier.forClass(CustomPageRequest)
                .withRedefinedSubclass(CustomPageRequestChild)
                .suppress(Warning.NONFINAL_FIELDS) // this is java bean
                .verify()
    }

}
