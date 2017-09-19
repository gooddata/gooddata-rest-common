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

class ISODateTimeDeserializerTest extends Specification {

    def "should deserialize"() {
        given:
        def dateTime = new DateTime(2012, 3, 20, 14, 31, 5, 3, DateTimeZone.UTC)
        def json = OBJECT_MAPPER.writeValueAsString(new ISODateClass(dateTime))

        when:
        def date = OBJECT_MAPPER.readValue(json, ISODateClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        date.getDate() == dateTime
        node.path('date').textValue() == '2012-03-20T14:31:05.003Z'
    }

}
