/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification
import spock.lang.Unroll

import static com.gooddata.util.Validate.*

class ValidateTest extends Specification {

    def "notNull should fail on null"() {
        when:
        notNull(null, 'foo')

        then:
        IllegalArgumentException exc = thrown()
        exc.message ==~ /.*foo.*can't be null.*/
    }

    @Unroll
    def "notEmpty should fail on #descArg"() {
        when:
        if (argClass == String) {
            notEmpty(arg as String, "foo")
        } else if (argClass == List) {
            notEmpty(arg as List, "foo")
        }

        then:
        IllegalArgumentException exc = thrown()
        exc.message ==~ ".*foo.*can't be ${desc}.*"

        where:
        desc    | arg  | argClass
        'null'  | null | String
        'empty' | ''   | String
        'empty' | ' '  | String
        'null'  | null | List
        'empty' | []   | List

        descArg = "$desc $argClass.simpleName"
    }

    @Unroll
    def "noNullElements should fail on #arg"() {
        when:
        noNullElements(arg, "foo")

        then:
        IllegalArgumentException exc = thrown()
        exc.message ==~ arg == null ? /.*foo.*can't be null.*/ : /.*foo.*contains null element.*/

        where:
        arg << [null, [null], [null].toArray()]
    }

    def "notNullState should fail on null"() {
        when:
        notNullState(null, 'foo')

        then:
        IllegalStateException exc = thrown()
        exc.message ==~ /.*foo.*is null.*/
    }

    def "isTrue should fail on false"() {
        when:
        isTrue(false, 'foo')

        then:
        IllegalArgumentException exc = thrown()
        exc.message ==~ /.*foo.*/
    }


}
