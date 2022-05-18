package com.oreilly.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings({"ResultOfMethodCallIgnored", "CommentedOutCode"})
@ExtendWith(MockitoExtension.class)
public class AddingMachineJUnit5Test {
    @Mock
    private List<Integer> mockList;

    @InjectMocks
    private AddingMachine machine;

    @Test
    public void getTotalUsingLoop() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        // Only requires the stub behavior,
        //  i.e., that the get(i) methods return the expected values
        assertEquals(1 + 2 + 3, machine.getTotalUsingLoop());

        // Verify the protocol -- that the mock methods are called
        //  the right number of times in the right order
        InOrder inOrder = inOrder(mockList);

        inOrder.verify(mockList).size();
        inOrder.verify(mockList, times(3)).get(anyInt());
    }

    @Test
    public void getTotalUsingAnyInt() {
        when(mockList.size()).thenReturn(3);
//        when(mockList.get(anyInt()))
//                .thenReturn(1)
//                .thenReturn(2)
//                .thenReturn(3);

        when(mockList.get(anyInt()))
                .thenReturn(1, 2, 3);

        // Only requires the stub behavior,
        //  i.e., that the get(i) methods return the expected values
        // assertThat(nc.getTotalUsingLoop(), is(equalTo(6)));
        assertEquals(1 + 2 + 3, machine.getTotalUsingLoop());

        // Verify the protocol -- that the mock methods are called
        //  the right number of times in the right order
        InOrder inOrder = inOrder(mockList);

        assertAll("verify stub methods called right num of times in proper order",
                () -> inOrder.verify(mockList).size(),
                () -> inOrder.verify(mockList, times(3)).get(anyInt()));
    }

    @Test
    public void getTotalUsingStream() {
        when(mockList.stream()).thenReturn(Stream.of(1, 2, 3));

        // Only requires the stub behavior,
        assertEquals(6, machine.getTotalUsingStream());

        // Verify the protocol -- that the mock methods are called
        //  the right number of times
        verify(mockList).stream();
    }

    @Test
    void getTotalUsingLoopCustomMatcher() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(anyInt()))
                .thenReturn(1, 2, 3);

        assertEquals(1 + 2 + 3, machine.getTotalUsingLoop());

        // Custom matcher: intThat takes an ArgumentMatcher<Integer>
        verify(mockList, times(3)).get(intThat(n -> n < 3));

        // This line throws an NPE. Don't use argThat for primitives.
        // verify(mockList, times(3)).get(argThat(n -> n < 3));
    }
}