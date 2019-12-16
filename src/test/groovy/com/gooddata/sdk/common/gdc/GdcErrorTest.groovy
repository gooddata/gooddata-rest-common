/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.gdc

import spock.lang.Specification

import static com.gooddata.sdk.common.util.ResourceUtils.readObjectFromResource

class GdcErrorTest extends Specification {

    def "should deserialize"() {
        when:
        def err = readObjectFromResource('/gdc/gdcError.json', GdcError)

        then:
        with(err) {
            errorClass == 'CLASS'
            trace == 'TRACE'
            message == 'MSG %s %s'
            component == 'COMPONENT'
            errorId == 'ID'
            errorCode == 'CODE'
            requestId == 'REQ'
            parameters != null
            parameters.length == Integer.valueOf(2)
            parameters*.toString() == ['PARAM1', 'PARAM2']
        }
    }

}
