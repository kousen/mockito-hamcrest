package com.oreilly;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProjectTest {

    @Test
    public void checkDuration() {
        Project p = new Project("p1", LocalDate.now().minusDays(1),
                LocalDate.now());

        assertThat(p, hasProperty("duration"));
        assertThat(p.getDuration(), is(equalTo(1)));
    }

    @Test
    public void checkAllProperties() {
        Project p1 = new Project("p", LocalDate.now().minusDays(1),
                LocalDate.now());
        Project p2 = new Project("p", LocalDate.now().minusDays(1),
                LocalDate.now());

        assertThat(p1, samePropertyValuesAs(p2));
    }
}