package com.oreilly;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CollectionsTests {
    private List<String> strings = Arrays.asList("this", "is", "a",
                                                 "list", "of", "strings");

    @Test
    public void numberOfElements() {
        assertThat(strings, hasSize(6));
    }

    @Test
    public void checkOrder() {
        assertThat(strings, contains("this", "is", "a",
                                     "list", "of", "strings"));
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
}

