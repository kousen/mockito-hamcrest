package com.oreilly.mockito;

import com.oreilly.NumberCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NumberCollectionAnnotationTest {
    @Mock
    private List<Integer> mockList;

    @InjectMocks
    private NumberCollection nc;

//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

//    @Before
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void getTotalUsingLoop() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        // Only requires the stub behavior,
        //  i.e., that the get(i) methods return the expected values
        assertEquals(6, nc.getTotalUsingLoop());

        // Verify the protocol -- that the mock methods are called
        //  the right number of times
        verify(mockList).size();
        verify(mockList, times(3)).get(anyInt());
        verify(mockList, never()).clear();
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