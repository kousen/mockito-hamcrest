package com.oreilly.mockito;

import com.oreilly.Publisher;
import com.oreilly.Subscriber;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PublisherTest {

    private Publisher pub = new Publisher();
    private Subscriber sub1 = mock(Subscriber.class);
    private Subscriber sub2 = mock(Subscriber.class);

    @Before
    public void setUp() {
        pub.addSubscriber(sub1);
        pub.addSubscriber(sub2);
    }

    @Test
    public void testSend() {
        pub.send("Hello");

        verify(sub1).receive("Hello");
        verify(sub2).receive("Hello");
    }

    @Test
    public void testSendWithBadSubscriber() {
        // sub1 throws an exception every time
        doThrow(RuntimeException.class).when(sub1).receive(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // sub2 still receives the messages
        verify(sub2, times(2)).receive(argThat(s -> s.matches("message \\d")));
    }
}