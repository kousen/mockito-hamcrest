package com.oreilly;

import org.junit.Test;

import static com.oreilly.IsNotNumber.notANumber;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CustomMatcherTests {
    @Test
    public void sqrtMinus1() {
        assertThat(Math.sqrt(-1), is(notANumber()));
    }
}
