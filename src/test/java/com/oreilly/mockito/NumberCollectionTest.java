package com.oreilly.mockito;

import com.oreilly.NumberCollection;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class NumberCollectionTest {

    @Test
    public void getTotalUsingLoop() {
        // Create a stubbed list
        List<Integer> mockList = mock(List.class);

        // Set the expectations on the stub
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        // Inject the stub into the class we want to test
        NumberCollection nc = new NumberCollection(mockList);

        // Test the method we care about
        assertEquals(1 + 2 + 3, nc.getTotalUsingLoop());

        // Verify the protocol between NumberCollection and the stubbed list
        verify(mockList).size();
        verify(mockList, times(3)).get(anyInt());
    }

    @Test // @SuppressWarnings("unchecked")
    public void getTotalUsingIterable() {
        List<Integer> mockList = mock(List.class);

        when(mockList.iterator()).thenReturn(
                Arrays.asList(1, 2, 3).iterator());

        NumberCollection nc = new NumberCollection(mockList);
        assertEquals(1 + 2 + 3, nc.getTotalUsingIterable());

        verify(mockList).iterator();
    }

    @Test
    public void getTotalUsingStream() {
        List<Integer> mockList = mock(List.class);
        when(mockList.stream()).thenReturn(Stream.of(1, 2, 3));

        NumberCollection nc = new NumberCollection(mockList);

        assertEquals(1 + 2 + 3, nc.getTotalUsingStream());

        verify(mockList).stream();
    }

    @Test  // Not using Mockito at all
    public void getTotalWithStubbedList() {
        List<Integer> stubbedList = Arrays.asList(1, 2, 3);

        NumberCollection nc = new NumberCollection(stubbedList);

        assertEquals(1 + 2 + 3, nc.getTotalUsingLoop());
        assertEquals(1 + 2 + 3, nc.getTotalUsingIterable());
        assertEquals(1 + 2 + 3, nc.getTotalUsingStream());

        // No built-in way to verify the method calls on stubbed list
    }

    @Test
    public void spyOnList() {
        // Spy on a real list
        List<Integer> spyList = spy(Arrays.asList(1, 2, 3));

        NumberCollection nc = new NumberCollection(spyList);

        assertEquals(1 + 2 + 3, nc.getTotalUsingLoop());
        assertEquals(1 + 2 + 3, nc.getTotalUsingIterable());
        assertEquals(1 + 2 + 3, nc.getTotalUsingStream());

        // Can verify a spy
        verify(spyList).size();
        verify(spyList, times(3)).get(anyInt());
        verify(spyList).iterator();
        verify(spyList).stream();
    }
}