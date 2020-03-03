package com.oreilly.mockito;

import com.oreilly.Publisher;
import com.oreilly.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PublisherTest {

    private final Publisher pub = new Publisher();
    private final Subscriber sub1 = mock(Subscriber.class);
    private final Subscriber sub2 = mock(Subscriber.class);

    @BeforeEach
    public void setUp() {
        pub.subscribe(sub1);
        pub.subscribe(sub2);
    }

    @Test
    public void testSend() {
        pub.send("Hello");

        verify(sub1).onNext("Hello");
        verify(sub2).onNext("Hello");
    }

    @Test
    public void testSendWithBadSubscriber() {
        // sub1 throws an exception every time
        doThrow(RuntimeException.class).when(sub1).onNext(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // Usually not necessary to verify the stubbed behavior
        assertAll(
                () -> assertThrows(RuntimeException.class,
                        () -> sub1.onNext("message 1")),
                () -> assertThrows(RuntimeException.class,
                        () -> sub1.onNext("message 2")));

        // sub2 still receives the messages
        verify(sub2, times(2))
                .onNext(argThat(s -> s.matches("message \\d")));
    }
}