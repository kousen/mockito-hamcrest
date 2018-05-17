package com.oreilly;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StringTests {
    @Test
    public void equality() {
        String s1 = "abcd";
        String s2 = "abcd";
        String s3 = "a bc d";

        assertThat(s1, is(s2));
        assertThat(s1, is(not(s3)));
        assertThat(s1, equalToIgnoringCase("ABcd"));
        assertThat(s1, equalToIgnoringWhiteSpace("  abcd  "));
        assertThat(s3, equalToIgnoringWhiteSpace("  a  bc\t d  "));
    }

    @Test
    public void containing() {
        String s = "this is a string";

        assertThat(s, containsString("this"));

        String[] split = s.split(" ");
        Stream.of(split)
              .forEach(word -> assertThat(s, containsString(word)));

        assertThat(s, startsWith("this"));
        assertThat(s, endsWith("ing"));
    }

    @Test
    public void emptyOrNulls() {
        String s = "";
        assertThat(s, isEmptyString());

        s = null;
        assertThat(s, isEmptyOrNullString());
    }
}
