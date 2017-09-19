/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import org.joda.time.LocalDate
import spock.lang.Specification

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class GDDateDeserializerTest extends Specification {

    def "should deserialize"() {
        given:
        def localDate = new LocalDate(2012, 3, 20)
        def json = OBJECT_MAPPER.writeValueAsString(new GDDateClass(localDate))

        when:
        def date = OBJECT_MAPPER.readValue(json, GDDateClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        date.getDate() == localDate
        node.path('date').textValue() == '2012-03-20'
    }

}
