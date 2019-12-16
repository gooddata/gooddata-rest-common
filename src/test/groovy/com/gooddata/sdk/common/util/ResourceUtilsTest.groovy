/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util

import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.sdk.common.util.ResourceUtils.readStringFromResource

class ResourceUtilsTest extends Specification {

    def "should read string from resource"() {
        expect:
        readStringFromResource('/test.txt') == 'foo'
    }

    @Unroll
    def "should fail on #type resource"() {
        when:
        readStringFromResource(resource)

        then:
        IllegalArgumentException ex = thrown()
        ex.message ==~ msgRegex

        where:
        type           | resource   || msgRegex
        'non existing' | '/foo.txt' || /.*foo.txt.*/
        'null'         | null       || /.*can't be null.*/
    }

}
