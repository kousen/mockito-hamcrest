package com.oreilly.mockito;

import com.oreilly.NumberCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@ExtendWith(MockitoExtension.class)
public class NumberCollectionSpyTest {
    @Spy
    private final List<Integer> spyList = new LinkedList<>();

    @InjectMocks
    private NumberCollection nc;

    @Test
    public void getTotalUsingForLoop() {
        spyList.add(1); spyList.add(2); spyList.add(3);

        assertThat(nc.getTotalUsingForLoop(), is(equalTo(6)));

        InOrder inOrder = inOrder(spyList);

        assertAll("verify stub methods called right num of times in proper order",
                () -> inOrder.verify(spyList).size(),
                () -> inOrder.verify(spyList, times(3)).get(anyInt()));

    }

    @Test
    void getTotalUsingForEach() {
        spyList.add(1); spyList.add(2); spyList.add(3);

        assertEquals(6, nc.getTotalUsingForEach());
        verify(spyList).iterator();
    }

    @Test
    public void getTotalUsingStream() {
        doReturn(Stream.of(1, 2, 3)).when(spyList).stream();

        assertEquals(6, nc.getTotalUsingStream());
        verify(spyList).stream();
    }

}