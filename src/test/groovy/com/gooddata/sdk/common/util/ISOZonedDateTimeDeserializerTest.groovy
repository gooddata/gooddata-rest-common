/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util

import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZoneId
import java.time.ZonedDateTime

import static com.gooddata.sdk.common.util.ResourceUtils.OBJECT_MAPPER

class ISOZonedDateTimeDeserializerTest extends Specification {

    @Unroll
    def "should deserialize for zone #zoneId"() {
        given:
        def zonedDateTime = new ISOZonedDateTimeClass(ZonedDateTime.of(2012, 3, 20, 14, 31, 5, 3000000, zoneId))
        def json = OBJECT_MAPPER.writeValueAsString(zonedDateTime)

        when:
        def date = OBJECT_MAPPER.readValue(json, ISOZonedDateTimeClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        //2012-03-20T13:31:05.003Z vs. 2012-03-20T14:31:05[$Zone]
        date.date.toInstant() == zonedDateTime.date.toInstant()
        node.path('date').textValue() == expectedDateTime

        where:
        zoneId                     | expectedDateTime
        ZoneId.of("UTC")           | '2012-03-20T14:31:05.003Z'
        ZoneId.of("Europe/Prague") | '2012-03-20T13:31:05.003Z'
    }

    @Unroll
    def "should deserialize with single digit date: #jsonDateTime"() {
        when:
        def dateClass = OBJECT_MAPPER.readValue(jsonDateTime, ISOZonedDateTimeClass)

        then:
        dateClass.date.toInstant().toEpochMilli() == milisFromEpoch

        where:
        jsonDateTime                          | milisFromEpoch
        '{"date":"2012-3-2T14:31:05.003Z"}'   | 1330698665003
        '{"date":"2012-03-02T14:31:05.003Z"}' | 1330698665003
        '{"date":"2012-12-30T14:31:05.003Z"}' | 1356877865003
        '{"date":"2012-1-30T14:31:05.003Z"}'  | 1327933865003
    }
}
