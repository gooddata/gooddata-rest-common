/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util


import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.ZoneOffset

import static com.gooddata.sdk.common.util.ResourceUtils.OBJECT_MAPPER

class GDLocalDateDeserializerTest extends Specification {

    def getSecondsFromEpoch = { GDLocalDateClass dateClass -> dateClass.getDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC) }

    def "should deserialize"() {
        given:
        def localDate = LocalDate.of(2012, 3, 20)
        def json = OBJECT_MAPPER.writeValueAsString(new GDLocalDateClass(localDate))

        when:
        def dateClass = OBJECT_MAPPER.readValue(json, GDLocalDateClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        dateClass.date == localDate
        node.path('date').textValue() == '2012-03-20'
    }

    @Unroll
    def "should deserialize with single digit date: #jsonDate"() {
        when:
        def localDate = OBJECT_MAPPER.readValue(jsonDate, GDLocalDateClass)

        then:
        getSecondsFromEpoch(localDate) == secondsFromEpoch

        where:
        jsonDate                | secondsFromEpoch
        '{"date":"2012-3-2"}'   | 1330646400
        '{"date":"2012-03-02"}' | 1330646400
        '{"date":"2012-12-30"}' | 1356825600
        '{"date":"2012-1-30"}'  | 1327881600
    }
}
