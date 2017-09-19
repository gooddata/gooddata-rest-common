/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import spock.lang.Specification

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class ISODateTimeSerializerTest extends Specification {

    def "should serialize"() {
        given:
        def isoDate = new ISODateClass(new DateTime(2012, 3, 20, 14, 31, 5, 3, DateTimeZone.UTC))

        when:
        def json = OBJECT_MAPPER.writeValueAsString(isoDate)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('date').textValue() == '2012-03-20T14:31:05.003Z'
    }

}
