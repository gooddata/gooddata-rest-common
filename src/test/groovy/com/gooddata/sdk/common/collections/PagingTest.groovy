/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.collections

import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.sdk.common.util.ResourceUtils.readObjectFromResource
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals
import static net.javacrumbs.jsonunit.core.util.ResourceUtils.resource
import static spock.util.matcher.HamcrestSupport.that

class PagingTest extends Specification {

    @Unroll
    def "should deserialize #type"() {
        when:
        Paging paging = readObjectFromResource("/collections/${resource}.json", Paging)

        then:
        paging.offset == offset
        paging.nextUri == nextUri

        where:
        type          | resource           | offset | nextUri
        'all'         | 'paging'           | '0'    | 'next'
        'offset only' | 'paging_no_next'   | '0'    | null
        'next only'   | 'paging_only_next' | null   | '/nextUri?offset=17'
    }


    def "should serialize"() {
        expect:
        that new Paging('/nextUri?offset=17'), jsonEquals(resource('collections/paging_only_next.json'))
    }

}
