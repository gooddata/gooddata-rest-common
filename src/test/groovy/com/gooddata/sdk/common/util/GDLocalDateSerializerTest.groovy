/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util

import spock.lang.Specification

import java.time.LocalDate

import static com.gooddata.sdk.common.util.ResourceUtils.OBJECT_MAPPER

class GDLocalDateSerializerTest extends Specification {

    def "should serialize"() {
        given:
        def date = new GDLocalDateClass(LocalDate.of(2012, 3, 20))

        when:
        def json = OBJECT_MAPPER.writeValueAsString(date)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('date').textValue() == '2012-03-20'
    }

}
