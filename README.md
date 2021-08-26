# GoodData REST Common [![Build Status](https://github.com/gooddata/gooddata-rest-common/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/gooddata/gooddata-rest-common/actions/workflows/build.yml) [![Javadocs](http://javadoc.io/badge/com.gooddata/gooddata-rest-common.svg)](http://javadoc.io/doc/com.gooddata/gooddata-rest-common) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.gooddata/gooddata-rest-common/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gooddata/gooddata-rest-common) [![Coverage Status](https://codecov.io/gh/gooddata/gooddata-rest-common/branch/master/graph/badge.svg)](https://app.codecov.io/gh/gooddata/gooddata-rest-common/branch/master)

The *GoodData REST Common* provides set of REST releated utility functions for Java.
It is free and open-source software provided "as-is" under the [BSD License](LICENSE.txt) as an official project by [GoodData Corporation](http://www.gooddata.com).

## Usage

The *GoodData REST Common* is available in Maven Central Repository, to use it from Maven add to `pom.xml`:

```xml
<dependency>
    <groupId>com.gooddata</groupId>
    <artifactId>gooddata-rest-common</artifactId>
    <version>{MAJOR}.{MINOR}.{PATCH}</version>
</dependency>
```

### Dependencies

The *GoodData REST Common* uses:
* the *Spring Framework* version 5.*
* the *Jackson JSON Processor* version 2.10.*
* the *Java Development Kit (JDK)* version 11 or later to build, can run on 8 and later

## Development

Build the library with `mvn package`

## Contribute

Found a bug? Please create an [issue](https://github.com/gooddata/gooddata-rest-common/issues).
