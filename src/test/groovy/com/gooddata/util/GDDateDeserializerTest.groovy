/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.util.ResourceUtils.OBJECT_MAPPER

class GDDateDeserializerTest extends Specification {

    def getSecondsFromEpoch = { GDDateClass dateClass -> dateClass.getDate().toDateTimeAtStartOfDay(DateTimeZone.UTC).getMillis() / 1000 }

    def "should deserialize"() {
        given:
        def localDate = new LocalDate(2012, 3, 20)
        def json = OBJECT_MAPPER.writeValueAsString(new GDDateClass(localDate))

        when:
        def dateClass = OBJECT_MAPPER.readValue(json, GDDateClass)
        def node = OBJECT_MAPPER.readTree(json)

        then:
        dateClass.getDate() == localDate
        node.path('date').textValue() == '2012-03-20'
    }

    @Unroll
    def "should deserialize with single digit date: #jsonDate"() {
        when:
        def localDate = OBJECT_MAPPER.readValue(jsonDate, GDDateClass)

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
