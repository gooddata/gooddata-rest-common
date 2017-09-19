/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.util

import spock.lang.Specification

class GoodDataToStringBuilderTest extends Specification {
    private static final String OUTER_CLASS_NAME = GoodDataToStringBuilderTest.simpleName
    private static final String TEST_CLASS_NAME = TestClass.simpleName
    private static final String ANOTHER_TEST_CLASS_NAME = AnotherTestClass.simpleName
    private static final String CLASS_WITH_ADDITIONAL_PARAM = ClassWithAdditionalParam.simpleName
    private static final String STRING_FIELD_VALUE = 'STRING_FIELD'
    private static final Long LONG_FIELD_VALUE = 10L
    private static final List<Integer> LIST_VALUE = [1, 2, 3]
    private static
    final String ANOTHER_TEST_CLASS_STRING_EXPECTED = "${OUTER_CLASS_NAME}.$ANOTHER_TEST_CLASS_NAME[stringField=$STRING_FIELD_VALUE]"

    def "test default toString non recursive"() {
        expect:
        new AnotherTestClass().toString() == ANOTHER_TEST_CLASS_STRING_EXPECTED
    }

    def "test default toString recursive"() {
        expect:
        new TestClass().toString() ==
                "${OUTER_CLASS_NAME}.$TEST_CLASS_NAME[stringField=$STRING_FIELD_VALUE,longField=$LONG_FIELD_VALUE,list=${LIST_VALUE.toString()},anotherTestClass=$ANOTHER_TEST_CLASS_STRING_EXPECTED]"
    }

    def "test toString include additional param"() {
        expect:
        new ClassWithAdditionalParam().toString() ==
                "${OUTER_CLASS_NAME}.$CLASS_WITH_ADDITIONAL_PARAM[newField=$STRING_FIELD_VALUE]"
    }


    static class TestClass {
        private Map<String, String> links = ['self': '/gdc/link']
        private String stringField = STRING_FIELD_VALUE
        private Long longField = LONG_FIELD_VALUE
        private String toBeExcludedField = 'SHOULD_NOT_BE_THERE'
        private String anotherToBeExcludedField = 'ALSO_SHOULD_NOT_BE_THERE'
        private List<Integer> list = LIST_VALUE
        private AnotherTestClass anotherTestClass = new AnotherTestClass()

        public Map<String, String> getLinks() {
            return links
        }

        public String getStringField() {
            return stringField
        }

        public Long getLongField() {
            return longField
        }

        public String getToBeExcludedField() {
            return toBeExcludedField
        }

        public String getAnotherToBeExcludedField() {
            return anotherToBeExcludedField
        }

        public List<Integer> getList() {
            return list
        }

        public AnotherTestClass getTestClass2() {
            return anotherTestClass
        }

        @Override
        public String toString() {
            return GoodDataToStringBuilder.defaultToString(this, 'toBeExcludedField', 'anotherToBeExcludedField')
        }
    }

    static class AnotherTestClass {
        private Map<String, String> links = ['self': '/gdc/link2']

        private String stringField = STRING_FIELD_VALUE

        public String getStringField() {
            return stringField
        }

        @Override
        public String toString() {
            return GoodDataToStringBuilder.defaultToString(this)
        }
    }

    static class ClassWithAdditionalParam {
        @Override
        public String toString() {
            return new GoodDataToStringBuilder(this).append('newField', STRING_FIELD_VALUE).toString()
        }
    }
}
