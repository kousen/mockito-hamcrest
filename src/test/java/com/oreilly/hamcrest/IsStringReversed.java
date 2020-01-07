package com.oreilly.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsStringReversed extends TypeSafeMatcher<String> {
    @Override
    protected boolean matchesSafely(String item) {
        String testString = new StringBuilder(item.toLowerCase()).reverse().toString();
        return item.toLowerCase().equals(testString);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is the same reversed");
    }

    public static Matcher<String> isStringReversed() {
        return new IsStringReversed();
    }
}
