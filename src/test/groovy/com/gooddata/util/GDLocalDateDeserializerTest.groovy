/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification

import java.time.LocalDate

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class GDLocalDateDeserializerTest extends Specification {

    def "should deserialize"() {
        given:
        def localDate = LocalDate.of(2012, 3, 20)
        def json = OBJECT_MAPPER.writeValueAsString(new GDLocalDateClass(localDate))

        when:
        def date = OBJECT_MAPPER.readValue(json, GDLocalDateClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        date.getDate() == localDate
        node.path('date').textValue() == '2012-03-20'
    }

}
