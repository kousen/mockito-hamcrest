package com.oreilly;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberCollectionTest {

    @Test
    public void getTotal() {
        List mockList = mock(List.class);
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        NumberCollection nc = new NumberCollection(mockList);

        assertEquals(6, nc.getTotal());
    }
}