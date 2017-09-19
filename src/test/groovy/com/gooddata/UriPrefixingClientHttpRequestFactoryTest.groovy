/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata

import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import spock.lang.Shared
import spock.lang.Specification

class UriPrefixingClientHttpRequestFactoryTest extends Specification {

    @Shared WRAPPED = new SimpleClientHttpRequestFactory()

    void setupSpec() {
        WRAPPED.setTaskExecutor(new SimpleAsyncTaskExecutor())
    }

    def "should create prefixed request"() {
        when:
        def request = requestFactory."$method" URI.create('/gdc/resource'), HttpMethod.GET

        then:
        request.URI.toString() == 'http://localhost:1234/gdc/resource'

        where:
        [requestFactory, method] << [
                [new UriPrefixingClientHttpRequestFactory(WRAPPED, 'http', 'localhost', 1234),
                 new UriPrefixingClientHttpRequestFactory(WRAPPED, 'http://localhost:1234')],
                ['createRequest', 'createAsyncRequest']
        ].combinations()
    }

    def "should not createAsyncRequest without async factory"() {
        given:
        ClientHttpRequestFactory wrapped = { uri, httpMethod -> null }
        UriPrefixingClientHttpRequestFactory requestFactory = new UriPrefixingClientHttpRequestFactory(wrapped, "http", "localhost", 1234)

        when:
        requestFactory.createAsyncRequest(URI.create("/gdc/resource"), HttpMethod.GET)

        then:
        thrown(UnsupportedOperationException)
    }

}
