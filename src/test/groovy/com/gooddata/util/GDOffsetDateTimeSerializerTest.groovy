/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class GDOffsetDateTimeSerializerTest extends Specification {

    @Unroll
    def "should serialize for zone #zoneId"() {
        given:
        def localDateTime = LocalDateTime.of(2012, 3, 20, 14, 31, 5, 3000000)
        def zoneOffset = (zoneId as ZoneId).rules.getOffset(localDateTime)
        def offsetDateTime = new GDOffsetDateTimeClass(OffsetDateTime.of(localDateTime, zoneOffset))

        when:
        def json = OBJECT_MAPPER.writeValueAsString(offsetDateTime)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('date').textValue() == expectedDateTime

        where:
        zoneId                     | expectedDateTime
        ZoneId.of("UTC")           | '2012-03-20 14:31:05'
        ZoneId.of("Europe/Prague") | '2012-03-20 13:31:05'
    }

    def "should serialize null"() {
        given:
        def dateTime = new GDOffsetDateTimeClass(null)

        when:
        def json = OBJECT_MAPPER.writeValueAsString(dateTime)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        node.path('date').isNull()
    }
}
