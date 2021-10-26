package com.oreilly.hamcrest;

import com.oreilly.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimpleAssertions {
    @Test
    public void instances() {
        assertThat(1, instanceOf(Integer.class));
        assertThat(1, is(instanceOf(Integer.class)));
        assertThat(1L, isA(Long.class));  // shorthand for instanceOf
    }

    @Test
    public void anyOf_allOf() {
        // evaluates both, because first is false
        assertThat("string", anyOf(is("str"), containsString("str")));

        // only evaluates first, because it is true (short circuits)
        assertThat("string", anyOf(containsString("str"), is("str")));

        assertThat(12, allOf(is(6 * 2), lessThan(20)));
    }

    @Test
    public void orderingOnComparable() {
        assertThat("abc", is(lessThan("def")));

        LocalDate now = LocalDate.now();
        LocalDate then = now.plusDays(2);
        assertThat(now, is(lessThan(then)));
        assertThat(then, is(greaterThanOrEqualTo(now)));

        Person hopper = new Person(1, "Grace", "Hopper",
                                   LocalDate.of(1906, Month.DECEMBER, 9));
        Person lovelace = new Person(2, "Ada", "Lovelace",
                                     LocalDate.of(1815, Month.DECEMBER, 10));
        assertThat(lovelace, is(greaterThan(hopper))); // last name alphabetical
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

    @Test
    public void checkSameInstance() {
        Person hopper1 = new Person(1, "Grace", "Hopper",
                                    LocalDate.of(1906, Month.DECEMBER, 9));
        Person hopper2 = new Person(1, "Grace", "Hopper",
                                    LocalDate.of(1906, Month.DECEMBER, 9));

        Person hopper3 = hopper1;

        assertThat(hopper2, is(hopper1));  // equals method comparison
        assertThat(hopper2, is(not(sameInstance(hopper3))));
        assertThat(hopper1, sameInstance(hopper3));  // references equality
    }

    @Test
    public void nullability() {
        Person hopper = null;
        assertThat(hopper, nullValue());
        assertThat(hopper, nullValue(Person.class));
        assertThat(hopper, is(nullValue()));

        hopper = new Person(1, "Grace", "Hopper",
                            LocalDate.of(1906, Month.DECEMBER, 9));
        assertThat(hopper, notNullValue());
        assertThat(hopper, notNullValue(Person.class));
    }
}
