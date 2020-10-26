package com.oreilly.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsNotNumber extends TypeSafeMatcher<Double> {
    @Override
    protected boolean matchesSafely(Double number) {
        return number.isNaN();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("not a number");
    }

    // Mimic the factory methods from Matchers.*
    public static Matcher<Double> notANumber() {
        return new IsNotNumber();
    }
}
