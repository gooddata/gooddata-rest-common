/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common

import com.gooddata.sdk.common.gdc.GdcError
import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.sdk.common.util.ResourceUtils.readObjectFromResource

class GoodDataRestExceptionTest extends Specification {

    @Unroll
    def "should create #type default instance"() {
        given:
        GoodDataRestException e = eErrorCode != null ? new GoodDataRestException(500, eRequestId, 'message', 'component', 'gdc.error', eErrorCode)
                : new GoodDataRestException(500, eRequestId, 'message', 'component', 'gdc.error')

        expect:
        with(e) {
            message == eMsg
            statusCode == 500
            requestId == eRequestId
            text == 'message'
            component == 'component'
            errorClass == 'gdc.error'
            errorCode == eErrorCode
        }

        where:
        eRequestId | eErrorCode | type                | eMsg
        'a123'     | 'code'     | 'full'              | '500: [request_id=a123] message'
        'a123'     | null       | 'without errorCode' | '500: [request_id=a123] message'
        null       | null       | 'without requestId' | '500: message'
    }

    @Unroll
    def "should create instance from gdc error with #type"() {
        given:
        GoodDataRestException e = new GoodDataRestException(500, 'a123', statusText, gdcError)

        expect:
        with(e) {
            message == eMsg
            statusCode == 500
            requestId == (gdcError ? 'REQ' : 'a123')
            text == (gdcError ? 'MSG PARAM1 PARAM2' : statusText)
            if (gdcError) {
                errorClass == 'CLASS'
                component == 'COMPONENT'
                errorCode == 'CODE'
            }
        }

        where:
        statusText | gdcError                                               | eMsg                                      | type
        'message'  | null                                                   | '500: [request_id=a123] message'          | 'null gdc error'
        null       | null                                                   | '500: [request_id=a123] Unknown error'    | 'null status text and gdc error'
        'message'  | readObjectFromResource('/gdc/gdcError.json', GdcError) | '500: [request_id=REQ] MSG PARAM1 PARAM2' | 'gdc error'
    }
}
