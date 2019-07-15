/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZoneId
import java.time.ZonedDateTime

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class ISOZonedDateTimeSerializerTest extends Specification {

    @Unroll
    def "should serialize for zone #zoneId"() {
        given:
        def zonedDateTime = new ISOZonedDateTimeClass(ZonedDateTime.of(2012, 3, 20, 14, 31, 5, 3000000, zoneId))

        when:
        def json = OBJECT_MAPPER.writeValueAsString(zonedDateTime)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('date').textValue() == expectedDateTime

        where:
        zoneId                     | expectedDateTime
        ZoneId.of("UTC")           | '2012-03-20T14:31:05.003Z'
        ZoneId.of("Europe/Prague") | '2012-03-20T13:31:05.003Z'
    }

    def "should serialize null"() {
        given:
        def dateTime = new ISOZonedDateTimeClass(null)

        when:
        def json = OBJECT_MAPPER.writeValueAsString(dateTime)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('date').isNull()
    }
}
