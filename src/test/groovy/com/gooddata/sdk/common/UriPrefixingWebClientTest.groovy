/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common

import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Shared
import spock.lang.Specification

class UriPrefixingWebClientTest extends Specification {

    @Shared
    def webClientBuilder = WebClient.builder()

    def "should create prefixed request"() {
        given:
        def prefixer = new UriPrefixer('http://localhost:1234')
        def requestFactory = new UriPrefixingWebClient(webClientBuilder, prefixer)

        when:
        def uri = prefixer.prefixUri(URI.create('/gdc/resource'))

        then:
        uri.toString() == 'http://localhost:1234/gdc/resource'
    }

    def "should handle invalid URI gracefully"() {
        given:
        def prefixer = new UriPrefixer('http://localhost:1234')
        def requestFactory = new UriPrefixingWebClient(webClientBuilder, prefixer)

        when:
        requestFactory.createRequest('/invalid uri', HttpMethod.GET)

        then:
        thrown(IllegalArgumentException)
    }

    def "should handle multiple constructors of UriPrefixer"() {
        when:
        def prefixer1 = new UriPrefixer('http://localhost:1234')
        def prefixer2 = new UriPrefixer(URI.create('http://localhost:1234'))

        then:
        prefixer1.getUriPrefix() == prefixer2.getUriPrefix()
    }
}
