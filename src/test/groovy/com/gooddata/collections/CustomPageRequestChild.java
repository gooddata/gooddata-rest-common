/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.collections;

/**
 * Class for testing {@link CustomPageRequest}'s equals' method inheritance
 */
public class CustomPageRequestChild extends CustomPageRequest {

    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(final String foo) {
        this.foo = foo;
    }

    @Override
    protected boolean canEqual(final Object o) {
        return o instanceof CustomPageRequestChild;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomPageRequestChild)) return false;
        if (!super.equals(o)) return false;

        final CustomPageRequestChild that = (CustomPageRequestChild) o;
        if (!(that.canEqual(this))) return false;

        return foo != null ? foo.equals(that.foo) : that.foo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (foo != null ? foo.hashCode() : 0);
        return result;
    }
}
