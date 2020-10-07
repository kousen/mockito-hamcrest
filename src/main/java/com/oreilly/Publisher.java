package com.oreilly;

import java.util.ArrayList;
import java.util.List;

// Adapted from a similar example in the Spock framework
public class Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber sub) {
        if (!subscribers.contains(sub)) {
            subscribers.add(sub);
        }
    }

    // Want to test this method
    public void send(String message) {
        for (Subscriber sub : subscribers) {
            try {
                sub.receive(message);
            } catch (Exception ignored) {
                // evil, but what can you do?
            }
        }

//        subscribers.forEach(s -> catchAndKillExceptions(s, message));
    }

    private void catchAndKillExceptions(Subscriber sub, String message) {
        try {
            sub.receive(message);
        } catch (Exception ignored) {
            // ignored
        }
    }
}

