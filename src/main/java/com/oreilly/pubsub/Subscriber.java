package com.oreilly.pubsub;

public interface Subscriber {
    void receive(String message);
}
