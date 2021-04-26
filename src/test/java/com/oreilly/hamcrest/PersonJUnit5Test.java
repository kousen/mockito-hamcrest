package com.oreilly.hamcrest;

import com.oreilly.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.Era;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PersonJUnit5Test {
    private final Person person = new Person("Grace", "Hopper",
            LocalDate.of(1906, Month.DECEMBER, 9));

    @Test
    public void checkEqualsMethod() {
        Person hopper = new Person("Grace", "Hopper",
                LocalDate.of(1906, Month.DECEMBER, 9));

        assertAll(
                () -> assertThat(person, is(equalTo(hopper))),
                () -> assertThat(person, equalTo(hopper)),
                () -> assertThat(person, is(hopper)) // same as is(equalTo(...))
        );
    }

    @Test
    public void checkNotEqual() {
        Person lovelace = new Person("Ada", "Lovelace",
                LocalDate.of(1815, Month.DECEMBER, 10));
        assertThat(person, is(not(equalTo(lovelace))));
    }

    @Test
    public void checkSameInstance() {
        Person hopper = person;
        assertThat(hopper, sameInstance(person));

        Person copy = new Person("Grace", "Hopper",
                LocalDate.of(1906, Month.DECEMBER, 9));
        assertThat(copy, not(sameInstance(person)));
        assertThat(copy, is(equalTo(person)));
    }

    @Test
    public void checkToString() {
        assertThat(person,
                hasToString("Person{first='Grace', last='Hopper', dob=1906-12-09}"));
    }

    @Test
    public void nonNullRandomNumbers() {
        assertThat(Math.random(), is(notNullValue(Double.class)));
        assertThat(Math.random(), closeTo(0.5, 0.5));
    }

    @Test
    public void checkNulls() {
        Person p = new Person(null, "Pythagoras", LocalDate.of(-500, Month.JANUARY, 1));
        assertThat(p.getFirst(), nullValue());
        assertThat(p.getLast(), notNullValue());
        assertThat(p.getLast(), notNullValue(String.class));
        Era era = p.getDob().getEra();
        assertThat(era, hasToString("BCE"));
        assertThat(LocalDate.now().getEra(), hasToString("CE"));
    }

    @Test
    public void properties() {
        assertThat("Person must have a first name", person, hasProperty("first"));
        assertThat(person, hasProperty("last"));
        assertThat(person, hasProperty("dob"));

        assertThat(person, hasProperty("last", equalTo("Hopper")));
        assertThat(person, hasProperty("name")); // supplied by getName() method (see below too)
    }

    @Test
    public void sameProperties() {
        Person hopper = new Person("Grace", "Hopper",
                LocalDate.of(1906, Month.DECEMBER, 9));

        // useful if Person did NOT have an equals method override
        assertThat(person, samePropertyValuesAs(hopper));
    }

    @Test
    public void equalButNotSameProperties() {
        Person hopper = new Person(1, "Grace", "Hopper",
                LocalDate.of(1906, Month.DECEMBER, 9));
        assertThat(person, not(samePropertyValuesAs(hopper)));
        assertThat(person, is(equalTo(hopper))); // works because equals(person) doesn't check ids

        // Vararg list of string properties to ignore
        assertThat(person, samePropertyValuesAs(hopper, "id"));
    }

    @Test
    public void hasDependentProperty() {
        assertThat(person, hasProperty("name"));
    }
}