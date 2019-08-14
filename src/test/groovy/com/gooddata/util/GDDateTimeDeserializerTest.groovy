/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class GDDateTimeDeserializerTest extends Specification {

    @Unroll
    def "should deserialize for zone #dateTimeZone"() {
        given:
        def dateTime = new DateTime(2012, 3, 20, 14, 31, 5, 3, dateTimeZone)
        def json = OBJECT_MAPPER.writeValueAsString(new GDDateTimeClass(dateTime))

        when:
        def date = OBJECT_MAPPER.readValue(json, GDDateTimeClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        date.getDate() == dateTime.withMillisOfSecond(0)
        node.path('date').textValue() == expectedDateTime

        where:
        dateTimeZone                        | expectedDateTime
        DateTimeZone.UTC                    | '2012-03-20 14:31:05'
        DateTimeZone.forID("Europe/Prague") | '2012-03-20 13:31:05'
    }
}
