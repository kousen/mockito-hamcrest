package com.oreilly.hamcrest;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class StringTests {
    @Test
    public void equality() {
        String s1 = "abcd";
        String s2 = "abcd";
        String s3 = "a bc   d";

        assertThat(s1, is(s2));
        assertThat(s1, is(not(s3)));
        assertThat(s1, equalToIgnoringCase("ABcd"));
        assertThat(s1, equalToCompressingWhiteSpace("  abcd  "));
        assertThat(s3, equalToCompressingWhiteSpace("  a  bc\t d  "));

        // JUnit approach
        assertEquals(s2, s1);

        // Hamcrest approach
        assertThat(s1, is(equalTo(s2)));
    }

    @Test
    public void containing() {
        String s = "this is a string";

        assertThat(s, containsString("this"));

        String[] split = s.split(" ");
        Arrays.stream(split)
              .forEach(word -> assertThat(s, containsString(word)));

        assertThat(s, startsWith("this"));
        assertThat(s, endsWith("ing"));

        String blank = "    ";
        assertThat(blank, containsString("  "));
        assertThat(blank, equalToCompressingWhiteSpace("         "));
        assertThat(blank, equalToCompressingWhiteSpace("  "));

        // From the JavaDocs on equalToIgnoringWhiteSpace
        assertThat("   my\tfoo  bar ", equalToCompressingWhiteSpace(" my  foo bar"));
        assertThat("   my\tfoo  bar ", equalToCompressingWhiteSpace("my foo bar"));
    }

    @Test
    public void emptyOrNulls() {
        String s = "";
        assertThat(s, is(emptyString()));
        assertThat(s, is(emptyOrNullString()));
        assertThat(null, is(emptyOrNullString()));
    }

    @Test
    public void checkCarriageReturnLineFeed() {
        assertThat("first\rsecond", equalToCompressingWhiteSpace("first\nsecond"));
    }
}
