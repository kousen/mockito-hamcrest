package com.oreilly.mockito;

import com.oreilly.NumberCollection;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NumberCollectionTest {

    @Test
    public void getTotal() {
        List<Integer> mockList = mock(List.class);
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        NumberCollection nc = new NumberCollection(mockList);

        assertEquals(6, nc.getTotal());

        // System.out.println(mockList.getClass().getName());

        verify(mockList).size();
        verify(mockList, times(3)).get(anyInt());
    }
}