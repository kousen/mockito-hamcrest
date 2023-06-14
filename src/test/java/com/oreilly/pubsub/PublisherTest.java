package com.oreilly.pubsub;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class PublisherTest {

    private final Publisher pub = new Publisher();
    private final Subscriber sub1 = mock(Subscriber.class);
    private final Subscriber sub2 = mock(Subscriber.class);

    @Before
    public void setUp() {
        pub.addSubscriber(sub1);
        pub.addSubscriber(sub2);
    }

    @Test
    public void testSend() {
        pub.send("Hello");

        verify(sub2).receive("Hello");
        verify(sub1).receive("Hello");
    }

    @Test  // Overspecified test -- forces a particular implementation
    public void testSendInOrder() {
        pub.send("Hello");

        // Questionable! Why are we mandating order?
        // What if the loop is run in parallel?
        InOrder inorder = inOrder(sub1, sub2);
        inorder.verify(sub1).receive("Hello");
        inorder.verify(sub2).receive("Hello");
    }

    @Test
    public void testSendWithBadSubscriber() {
        // Does not compile
        // when(sub1.receive(anyString())).thenThrow(new RuntimeException());

        // sub1 throws an exception every time
        doThrow(RuntimeException.class).when(sub1).receive(anyString());

        // Not necessary, since void methods already do nothing
        // doNothing().when(sub2).receive(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // sub1 receive method was called twice, even though it threw exception
        verify(sub1, times(2)).receive(anyString());

        // sub2 still receives the messages, because the publisher caught the exception
        verify(sub2, times(2)).receive(anyString());

        // called with strings that match the given pattern
        verify(sub2, times(2))
                .receive(argThat(s -> s.matches("message \\d")));

    }
}