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

class GDZonedDateTimeDeserializerTest extends Specification {

    @Unroll
    def "should deserialize for zone #zoneId"() {
        given:
        def zonedDateTime = new GDZonedDateTimeClass(ZonedDateTime.of(2012, 3, 20, 14, 31, 5, 3000000, zoneId))
        def json = OBJECT_MAPPER.writeValueAsString(zonedDateTime)

        when:
        def date = OBJECT_MAPPER.readValue(json, GDZonedDateTimeClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        date.getDate().toInstant() == zonedDateTime.date.withNano(0).toInstant()
        node.path('date').textValue() == expectedDateTime

        where:
        zoneId                     | expectedDateTime
        ZoneId.of("UTC")           | '2012-03-20 14:31:05'
        ZoneId.of("Europe/Prague") | '2012-03-20 13:31:05'
    }
}
