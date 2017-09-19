/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class BooleanIntegerSerializerTest extends Specification {

    @Unroll
    def "should serialize #value"() {
        when:
        def json = OBJECT_MAPPER.writeValueAsString(new BooleanIntegerClass(value))
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('foo').numberValue().intValue() == serialized

        where:
        value || serialized
        true  || 1
        false || 0
    }
}
