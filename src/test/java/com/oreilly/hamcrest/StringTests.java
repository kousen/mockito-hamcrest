package com.oreilly.hamcrest;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StringTests {
    @Test
    public void equality() {
        String s1 = "abcd";
        String s2 = "abcd";
        String s3 = "a bc   d";

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
        Arrays.stream(split)
              .forEach(word -> assertThat(s, containsString(word)));

        assertThat(s, startsWith("this"));
        assertThat(s, endsWith("ing"));

        String blank = "    ";
        assertThat(blank, containsString("  "));
        assertThat(blank, equalToIgnoringWhiteSpace("         "));
        assertThat(blank, equalToIgnoringWhiteSpace("  "));

        // From the JavaDocs on equalToIgnoringWhiteSpace
        assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"));
        assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace("my foo bar"));
    }

    @Test
    public void emptyOrNulls() {
        String s = "";
        assertThat(s, isEmptyString());

        assertThat(null, isEmptyOrNullString());
    }
}
