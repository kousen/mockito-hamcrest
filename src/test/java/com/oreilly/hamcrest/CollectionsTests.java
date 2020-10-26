package com.oreilly.hamcrest;

import com.oreilly.Person;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CollectionsTests {
    private final List<String> strings = Arrays.asList("this", "is", "a",
                                                 "list", "of", "strings");

    @Test
    public void numberOfElements() {
        assertThat(strings, hasSize(6));
    }

    @Test
    public void checkOrder() {
        assertThat(strings, contains("this", "is", "a",
                                     "list", "of", "strings"));
        assertThat(strings, not(contains("of", "is", "list",
                                         "a", "this", "strings")));
    }

    @Test
    public void elementsInAnyOrder() {
        assertThat(strings, containsInAnyOrder("a", "is", "list",
                                               "of", "strings", "this"));
    }

    @Test
    public void eachWordContainsAOI() {
        assertThat(strings, everyItem(anyOf(containsString("a"),
                                            containsString("i"),
                                            containsString("o"))));
        // From the JavaDocs on everyItem
        assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")));
    }

    @Test
    public void checkItems() {
        assertThat(strings, hasItem("list"));
    }

    @Test
    public void checkItemsInArray() {
        String[] stringArray = "this is an array of strings".split(" ");
        assertThat(stringArray, hasItemInArray("array"));
        assertThat(stringArray, arrayContainingInAnyOrder("an", "is", "array",
                                                          "of", "this", "strings"));
    }

    @Test
    public void itemsGreaterThan() {
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9);
        assertThat(nums, everyItem(greaterThanOrEqualTo(1)));
    }

    @Test
    public void hasPersonInArray() {
        Person[] people = new Person[3];
        Person hopper = new Person("Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9));
        Person lovelace = new Person("Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10));
        Person vonNeuman = new Person("John", "von Neuman", LocalDate.of(1903, Month.DECEMBER, 28));
        people[0] = hopper;
        people[1] = new Person("Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10));
        people[2] = lovelace;

        assertThat(people, hasItemInArray(
                new Person("Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9))));
        assertThat(people, hasItemInArray(lovelace));
        assertThat(people, not(hasItemInArray(vonNeuman)));
    }
}

