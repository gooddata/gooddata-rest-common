/*
 * Copyright (C) 2007-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.common.collections

import nl.jqno.equalsverifier.EqualsVerifier
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat
import static org.springframework.web.util.UriComponentsBuilder.fromUriString

class UriPageRequestTest extends Specification {

    def "should not construct with null"() {
        when:
        new UriPageRequest(null)

        then:
        IllegalArgumentException exc = thrown()
        exc.message ==~ /.*pageUri can't be null.*/
    }

    def 'test get page uri'() {
        given:
        final UriPageRequest uri = new UriPageRequest('uri')

        expect:
        assertThat(uri.getPageUri(null).toString(), is('uri'))

    }

    def "should updateWithPageParams"() {
        given:
        UriPageRequest uri = new UriPageRequest('uri?offset=god&limit=10')
        UriComponentsBuilder builder = fromUriString('/this/is/{template}').query('other=false')

        when:
        uri.updateWithPageParams(builder)
        UriComponents components = builder.build()

        then:
        components
        components.path == '/this/is/{template}'
        components.queryParams == ['other': ['false'], 'offset': ['god'], 'limit': ['10']]

        and: "is idempotent operation"
        uri.updateWithPageParams(builder)
        uri.updateWithPageParams(builder)

        then:
        builder.build() == components
    }

    def "should not double encode"() {
        expect:
        new UriPageRequest('uri?test=foo%20bar').getPageUri(null).toString() == 'uri?test=foo%20bar'
    }

    def "should verify equals"() {
        expect:
        EqualsVerifier.forClass(UriPageRequest).usingGetClass().verify()
    }

    def "should have correct toString"() {
        expect:
        new UriPageRequest('abc').toString() == 'UriPageRequest[pageUri=abc]'
    }
}
