/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata

import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

class UriPrefixerTest extends Specification {

    def "should fail on null prefix"() {
        when:
        if (clazz == URI)
            new UriPrefixer((URI) null)
        else
            new UriPrefixer((String) null)

        then:
        thrown(IllegalArgumentException)

        where:
        clazz << [String, URI]

    }

    def "should return uri prefix"() {
        expect:
        new UriPrefixer('foo').uriPrefix.toString() == 'foo'
    }

    def "should merge uris"() {
        given:
        def prefix = "http://localhost:1/uploads$s1"
        def toMerge = "${s2}test$s3"
        def merged = "http://localhost:1/uploads/test$s3"

        expect:
        new UriPrefixer(prefix).mergeUris(toMerge).toString() == merged

        where:
        [s1, s2, s3] << ([['', '/']] * 3).combinations()
    }

    def "should not double encode query param"() {
        given:
        def prefixer = new UriPrefixer('http://localhost:1/uploads')
        def uri = UriComponentsBuilder.fromPath('/foo').queryParam('bar', '\n').build().toUri()

        expect:
        prefixer.mergeUris(uri).toString() == 'http://localhost:1/uploads/foo?bar=%0A'
    }

    def "should handle absolute uris"() {
        given:
        def prefixer = new UriPrefixer('https://localhost:1')

        expect:
        prefixer.mergeUris('http://localhost:1/uploads/test/').toString() == 'https://localhost:1/uploads/test/'
    }


}
