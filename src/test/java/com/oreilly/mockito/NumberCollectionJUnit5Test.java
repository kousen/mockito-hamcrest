package com.oreilly.mockito;

import com.oreilly.NumberCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@ExtendWith(MockitoExtension.class)
public class NumberCollectionJUnit5Test {
    @Mock
    private List<Integer> mockList;

    @InjectMocks
    private NumberCollection nc;

    @Test
    public void getTotalUsingLoop() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        // Only requires the stub behavior,
        //  i.e., that the get(i) methods return the expected values
        assertThat(nc.getTotalUsingLoop(), is(equalTo(6)));

        // Verify the protocol -- that the mock methods are called
        //  the right number of times in the right order
        InOrder inOrder = inOrder(mockList);

        assertAll("verify stub methods called right num of times in proper order",
                () -> inOrder.verify(mockList).size(),
                () -> inOrder.verify(mockList, times(3)).get(anyInt()));
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
        assertThat(nc.getTotalUsingLoop(), is(equalTo(6)));

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
        assertEquals(6, nc.getTotalUsingStream());

        // Verify the protocol -- that the mock methods are called
        //  the right number of times
        verify(mockList).stream();
    }

}