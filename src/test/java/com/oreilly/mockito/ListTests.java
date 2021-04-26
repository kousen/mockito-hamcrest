package com.oreilly.mockito;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ListTests {

    @Test
    public void basicListMock() {
        List<String> mockedList = mock(List.class);

        // These would be called inside the class under test (not the mock)
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        // verify(mockedList).add("two"); // fails because diff arg
        verify(mockedList).clear();
    }

    // JUnit 5 allows method parameters
//    @Test
//    public void basicListMockWithParam(@Mock List<String> mockedList) {
//        // These would be called inside the class under test (not the mock)
//        mockedList.add("one");
//        mockedList.clear();
//
//        //verification
//        verify(mockedList).add("one");
//        // verify(mockedList).add("two"); // fails because diff arg
//        verify(mockedList).clear();
//    }

    @Test(expected = RuntimeException.class)
    public void stubbedList() {
        //You can mock concrete classes, not just interfaces
        LinkedList<String> mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
        //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
        verify(mockedList).get(0);
    }

    @Test
    public void chainedMethodCalls() {
        List<Integer> mockedList = mock(List.class);

        when(mockedList.get(anyInt()))
                .thenReturn(0)  // first call to get returns 0
                .thenReturn(1)  // second call returns 1
                .thenReturn(2); // third and subsequent calls returns 2

        // tests that the mock is "stubbed" properly
        assertThat(0, is(mockedList.get(999)));
        assertThat(1, is(mockedList.get(999)));
        assertThat(2, is(mockedList.get(999)));
        assertThat(2, is(mockedList.get(999)));

        // tests the protocol, i.e. size method is called the expected number of times
        verify(mockedList, times(4)).get(anyInt());
    }

    @Test
    public void chainedMethodCallsAsArguments() {
        List<String> mockedList = mock(List.class);

        when(mockedList.size())
                .thenReturn(0, 1, 2);

        assertThat(0, is(mockedList.size()));
        assertThat(1, is(mockedList.size()));
        assertThat(2, is(mockedList.size()));
        assertThat(2, is(mockedList.size()));

        verify(mockedList, times(4)).size();
    }

    @Test
    public void argumentMatchers() {
        List<String> mockedList = mock(List.class);

        //stubbing using built-in anyInt() argument matcher
        when(mockedList.get(anyInt())).thenReturn("element");

        //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
        // when(mockedList.contains(argThat(isValid()))).thenReturn("element");

        //following prints "element"
        assertThat("element", is(mockedList.get(999)));  // Hamcrest matcher
        IntStream.rangeClosed(1, 1000)
                 .forEach(x -> assertThat("element", is(mockedList.get(x))));

        //you can also verify using an argument matcher
        verify(mockedList, times(1001)).get(anyInt());

        mockedList.add("abcdef");

        // custom argument matcher as anonymous inner class
        verify(mockedList).add(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String someString) {
                return someString.length() > 5;
            }
        }));

        // custom argument matcher implemented as lambda expression
        verify(mockedList).add(argThat(someString -> someString.length() > 5));
    }

    @Test
    public void argMatcher() {
        List<String> mockedList = mock(List.class);

        when(mockedList.add(anyString())).thenReturn(true);

        assertTrue(mockedList.add("abcdef"));
    }

//    private List<String> createMyList(String... args) {
//        return Arrays.stream(args)
//                .collect(Collectors.toList());
//    }
//
//    @Test
//    public void methodWithMultipleArgs() {
//        List<String> strings = mock(List.class);
//
//        verify(strings).addStrings(eq("one"), anyString());
//    }

    @Test
    public void argThatDemo() {
        List<String> mockedList = mock(List.class);

        when(mockedList.add(argThat(s -> s.length() > 5)))
                .thenReturn(true);

        assertTrue(mockedList.add("123456"));
        assertFalse(mockedList.add("1234"));

        // Use anonymous inner class that could be replaced with lambda
        verify(mockedList).add(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.length() > 5;
            }
        }));

        // Java lambda expression implementation of ArgumetMatcher interface
        verify(mockedList).add(argThat(s -> s.length() > 5));
        verify(mockedList, times(2)).add(anyString());
    }

    @Test
    public void argCaptor() {
        List<String> mockedList = mock(List.class);
        when(mockedList.add(anyString())).thenReturn(true);

        // Somewhere in the test, this gets called:
        mockedList.add("abc");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockedList).add(captor.capture());
        assertThat("abc", is(equalTo(captor.getValue())));
    }

    @Test
    public void verifyWithTimes() {
        List<String> mockedList = mock(List.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");


        // Don't care what order methods were called in
        mockedList.add("four");
        mockedList.add("five");

        verify(mockedList).add("five");
        verify(mockedList).add("four");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");

        verify(mockedList, atLeast(6)).add(anyString());
    }

    @Test
    public void verifyOrdering() {
        // A. Single mock whose methods must be invoked in a particular order
        List<String> singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first", then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List<String> firstMock = mock(List.class);
        List<String> secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        inOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

    @Test(expected = RuntimeException.class)
    public void exceptionHandling() {
        List<String> mockedList = mock(List.class);
        doThrow(new RuntimeException()).when(mockedList).clear();
        // when(mockedList.clear()).thenThrow(new RuntimeException());

        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void mockFinalClassLocalDate() {
        LocalDate mockDate = mock(LocalDate.class);

        when(mockDate.toString()).thenReturn("1969-07-20");
        when(mockDate.getYear()).thenReturn(1969);

        assertThat(mockDate, hasToString("1969-07-20"));
        int year = mockDate.getYear();

        verify(mockDate).getYear();
    }
}
