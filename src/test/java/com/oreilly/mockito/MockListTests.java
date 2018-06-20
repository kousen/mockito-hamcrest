package com.oreilly.mockito;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class MockListTests {

    @Test
    public void basicListMock() {
        List<String> mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

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
        List<String> mockedList = mock(List.class);

        when(mockedList.size())
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(2);

        System.out.println(mockedList.size());
        System.out.println(mockedList.size());
        System.out.println(mockedList.size());
        System.out.println(mockedList.size());

        verify(mockedList, times(4)).size();
    }

    @Test
    public void chainedMethodCallsAsArguments() {
        List<String> mockedList = mock(List.class);

        when(mockedList.size())
                .thenReturn(0, 1, 2);

        System.out.println(mockedList.size());
        System.out.println(mockedList.size());
        System.out.println(mockedList.size());
        System.out.println(mockedList.size());

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
        System.out.println(mockedList.get(999));

        //you can also verify using an argument matcher
        verify(mockedList).get(anyInt());

        mockedList.add("abcdef");

        //argument matchers can also be written as Java 8 Lambdas
        verify(mockedList).add(argThat(someString -> someString.length() > 5));
    }

    @Test
    public void argMatcher() {
        List<String> mockedList = mock(List.class);

        when(mockedList.add(anyString())).thenReturn(true);

        mockedList.add("abcdef");

        // Use anonymous inner class that could be replaced with lambda
        verify(mockedList).add(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.length() > 5;
            }
        }));
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

        //following will make sure that add is first called with "was added first, then with "was added second"
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

    @Test @Ignore
    public void outOfBoundsException() {
        String[] strings = mock(String[].class);

        when(strings[3]).thenThrow(new ArrayIndexOutOfBoundsException());
    }

    @Test(expected = RuntimeException.class)
    public void exceptionHandling() {
        List<String> mockedList = mock(List.class);
        doThrow(new RuntimeException()).when(mockedList).clear();
        // when(mockedList.clear()).thenThrow(new RuntimeException())

        //following throws RuntimeException:
        mockedList.clear();
    }
}
