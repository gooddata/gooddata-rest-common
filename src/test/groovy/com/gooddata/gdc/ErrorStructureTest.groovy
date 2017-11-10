/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.gdc

import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.util.ResourceUtils.readObjectFromResource

class ErrorStructureTest extends Specification {

    @Unroll
    def "should deserialize #form"() {
        when:
        def errStructure = readObjectFromResource("/gdc/${form}.json", ErrorStructure)

        then:
        with(errStructure) {
            errorClass == 'CLASS'
            trace == 'TRACE'
            message == msgValue
            component == 'COMPONENT'
            errorId == 'ID'
            errorCode == 'CODE'
            requestId == 'REQ'
            parameters != null
            parameters.length == params.size()
            parameters*.toString() == params
            formattedMessage == toStringValue
        }
        errStructure.toString() == toStringValue

        where:
        form             | msgValue       | params
        'errorStructure' | 'MSG %s %s %d' | ['PARAM1', 'PARAM2', '3']
        'gdcError'       | 'MSG %s %s'    | ['PARAM1', 'PARAM2']

        toStringValue = 'MSG ' + params.join(' ')
    }
}
