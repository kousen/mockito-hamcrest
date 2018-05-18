package com.oreilly;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    public void getTotal() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(1)).thenReturn(2);
        when(mockList.get(2)).thenReturn(3);

        assertEquals(6, nc.getTotal());
    }
}