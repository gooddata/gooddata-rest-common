/*
 * Copyright (C) 2007-2020, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.util


import nl.jqno.equalsverifier.EqualsVerifier
import org.springframework.util.LinkedMultiValueMap
import spock.lang.Specification

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class SpringMutableUriTest extends Specification {

    def "should copy"() {
        given:
        def original = new SpringMutableUri('/some/uri?p=val')

        when:
        def copy = original.copy()

        then:
        copy instanceof SpringMutableUri
        (copy as SpringMutableUri).getUriComponents() == original.getUriComponents()

        and:
        copy.replaceQueryParam('p', 'val2')

        then:
        original.toUriString() == '/some/uri?p=val'
    }

    def "should replace query param"() {
        given:
        def mutableUri = new SpringMutableUri(URI.create('/some/uri?p=val'))

        when:
        mutableUri.replaceQueryParam('p', 'a', 2)

        then:
        mutableUri.toUri() == URI.create('/some/uri?p=a&p=2')
    }

    def "should replace query params"() {
        given:
        def mutableUri = new SpringMutableUri(URI.create('/some/uri?p=pval&q=qval'))
        def replace = new LinkedMultiValueMap<String, String>()
        replace.add('p', 'a')
        replace.add('p', '2')

        when:
        mutableUri.replaceQueryParams(replace)

        then:
        mutableUri.toUri() == URI.create('/some/uri?p=a&p=2')
    }

    def "should verify equals"() {
        expect:
        EqualsVerifier.forClass(SpringMutableUri)
                .withPrefabValues(Charset, StandardCharsets.UTF_8, StandardCharsets.US_ASCII)
                .usingGetClass()
                .verify()
    }
}
