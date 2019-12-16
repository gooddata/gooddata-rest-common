/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util

import com.fasterxml.jackson.databind.node.JsonNodeType
import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.sdk.common.util.ResourceUtils.OBJECT_MAPPER

class BooleanDeserializerTest extends Specification {

    @Unroll
    def "should deserialize #type #value"() {
        given:
        def json = OBJECT_MAPPER.writeValueAsString(typeClass.newInstance(value))

        when:
        def deserialized = OBJECT_MAPPER.readValue(json, typeClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        deserialized.isFoo() == value
        node.path('foo').nodeType == nodeType

        where:
        type      | value
        'integer' | true
        'integer' | false
        'string'  | true
        'string'  | false

        nodeType = type == 'integer' ? JsonNodeType.NUMBER : JsonNodeType.STRING
        typeClass = Class.forName("com.gooddata.sdk.common.util.Boolean${type.capitalize()}Class")
    }
}
