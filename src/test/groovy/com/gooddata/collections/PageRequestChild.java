/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.collections;

/**
 * Class for testing {@link PageRequest}'s equals' method inheritance
 */
public class PageRequestChild extends PageRequest {

    private String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(final String foo) {
        this.foo = foo;
    }

    @Override
    protected boolean canEqual(final Object o) {
        return o instanceof PageRequestChild;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PageRequestChild)) return false;
        if (!super.equals(o)) return false;

        final PageRequestChild that = (PageRequestChild) o;
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
