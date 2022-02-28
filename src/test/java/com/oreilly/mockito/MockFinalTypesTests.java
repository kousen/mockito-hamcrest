package com.oreilly.mockito;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SuppressWarnings("CommentedOutCode")
public class MockFinalTypesTests {
    @Test
    public void mockFinalClassLocalDate() {
        LocalDate mockDate = mock(LocalDate.class);

        when(mockDate.toString()).thenReturn("1969-07-20");
        when(mockDate.getYear()).thenReturn(1969);

        assertThat(mockDate).hasToString("1969-07-20");
        int year = mockDate.getYear();
        assertThat(year).isEqualTo(1969);

        verify(mockDate).getYear();
    }

    @Test
    @Disabled("Mockito cannot mock wrapper types, String.class, or Class.class")
    public void mockFinalClassString() {
//        String mockString = mock(String.class);
//
//        when(mockString).thenReturn("hello");
//        when(mockString.length()).thenReturn(5);
//
//        assertThat(mockString).hasToString("hello");
//        int length = mockString.length();
//        assertThat(length).isEqualTo(5);
//
//        verify(mockString).length();
    }

    @Test
    public void mockAnEnum() {
        Month mockMonth = mock(Month.class);

        when(mockMonth.getValue()).thenReturn(7);
        when(mockMonth.name()).thenReturn("July");

        assertThat(mockMonth.name()).isEqualTo("July");
        int value = mockMonth.getValue();
        assertThat(value).isEqualTo(7);

        verify(mockMonth).getValue();
        verify(mockMonth).name();
    }
}
