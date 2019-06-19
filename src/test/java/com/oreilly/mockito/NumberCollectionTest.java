package com.oreilly.mockito;

import com.oreilly.NumberCollection;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NumberCollectionTest {

    @Test
    public void getTotalUsingLoop() {
        List<Integer> mockList = mock(List.class);
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        NumberCollection nc = new NumberCollection(mockList);

        assertEquals(1 + 2 + 3, nc.getTotalUsingLoop());

        verify(mockList).size();
        verify(mockList, times(3)).get(anyInt());
    }

    @Test
    public void getTotalUsingStream() {
        List<Integer> mockList = mock(List.class);
        when(mockList.stream()).thenReturn(
                Stream.of(1, 2, 3));

        NumberCollection nc = new NumberCollection(mockList);

        assertEquals(1 + 2 + 3, nc.getTotalUsingStream());

        verify(mockList).stream();
    }

    @Test  // Not using Mockito at all
    public void getTotalWithStubbedList() {
        List<Integer> stubbedList = Arrays.asList(1, 2, 3);

        NumberCollection nc = new NumberCollection(stubbedList);

        assertEquals(1 + 2 + 3, nc.getTotalUsingLoop());
        assertEquals(1 + 2 + 3, nc.getTotalUsingStream());

        // No built-in way to verify the method calls on stubbed list
    }
}