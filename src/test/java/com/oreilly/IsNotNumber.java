package com.oreilly;

import org.hamcrest.Description;
import org.hamcrest.Factory;
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

    @Factory
    public static Matcher<Double> notANumber() {
        return new IsNotNumber();
    }
}
