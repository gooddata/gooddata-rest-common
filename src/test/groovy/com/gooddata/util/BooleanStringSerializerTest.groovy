/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class BooleanStringSerializerTest extends Specification {

    @Unroll
    def "should serialize #value"() {
        when:
        def json = OBJECT_MAPPER.writeValueAsString(new BooleanStringClass(value))
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('foo').textValue() == serialized

        where:
        value || serialized
        true  || '1'
        false || '0'

    }
}
