/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common

import org.springframework.http.HttpMethod
import org.springframework.http.client.SimpleClientHttpRequestFactory
import spock.lang.Shared
import spock.lang.Specification

class UriPrefixingClientHttpRequestFactoryTest extends Specification {

    @Shared
    def WRAPPED = new SimpleClientHttpRequestFactory()

    def "should create prefixed request"() {
        when:
        def request = requestFactory.createRequest(URI.create('/gdc/resource'), HttpMethod.GET)

        then:
        request.URI.toString() == 'http://localhost:1234/gdc/resource'

        where:
        requestFactory << [
            new UriPrefixingClientHttpRequestFactory(WRAPPED, 'http', 'localhost', 1234),
            new UriPrefixingClientHttpRequestFactory(WRAPPED, 'http://localhost:1234')
        ]
    }
}
