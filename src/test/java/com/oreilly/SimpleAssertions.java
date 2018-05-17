package com.oreilly;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleAssertions {
    @Test
    public void instances() {
        assertThat(1, instanceOf(Integer.class));
        assertThat(1L, isA(Long.class));  // shorthand for instanceOf
    }

    @Test
    public void anyOf_allOf() {
        assertThat("string", anyOf(is("str"), containsString("str")));

        assertThat(12, allOf(is(6 * 2), lessThan(20)));
    }

    @Test
    public void numerical() {
        assertThat(12, greaterThanOrEqualTo(10));
        assertThat(10, lessThan(15));
        assertThat(3.14159, closeTo(Math.PI, 0.0001));

        BigDecimal a = new BigDecimal("1.2345");
        BigDecimal b = new BigDecimal("1.234");
        assertThat(a, closeTo(b, new BigDecimal("0.001")));
    }
}
