/*
 * The MIT License
 *
 * Copyright (C) 2014 Tilmann Kuhn
 *
 * http://www.object-zoo.net
 * mailto:events4j@object-zoo.net
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.objectzoo.events.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;

/**
 * This helper class is a registry that is used by the event distributors to store the subscribers
 * that subscribed for the event.
 *
 * @param <SubscriberType> the type of subscriber this registry holds
 * @author tilmann
 */
public class EventSubscriberRegistry<SubscriberType>
{
    List<SubscriberType> subscribers = emptyList();

    /**
     * Adds the given subscriber to this registry if it is not already subscribed. This is checked
     * using the {@link Object#equals(Object)} of the subscriber.
     *
     * @param subscriber the subscriber to be added
     */
    public void subscribe(SubscriberType subscriber) throws IllegalArgumentException
    {
        Objects.requireNonNull(subscriber);

        addSubscriber(subscriber);
    }

    /**
     * This adds a subscriber to the subscribers by creating a new subscriber list and replacing the
     * old one. This is a workaround since {@link CopyOnWriteArrayList} could have been used
     * instead. But then it would not compile for GWT.
     *
     * @param subscriber the subscriber to be added
     */
    private synchronized void addSubscriber(SubscriberType subscriber)
    {
        if (!subscribers.contains(subscriber))
        {
            ArrayList<SubscriberType> newSubscribers = new ArrayList<>(subscribers);
            newSubscribers.add(subscriber);
            subscribers = newSubscribers;
        }
    }

    /**
     * Removes the given subscriber from this registry.
     *
     * @param subscriber the subscriber to be removed
     */
    public void unsubscribe(SubscriberType subscriber) throws IllegalArgumentException
    {
        Objects.requireNonNull(subscriber);

        removeSubscriber(subscriber);
    }

    /**
     * This removes a subscriber from the subscribers by creating a new subscriber list and
     * replacing the old one. This is a workaround since {@link CopyOnWriteArrayList} could have
     * been used instead. But then it would not compile for GWT.
     *
     * @param subscriber the subscriber to be removed
     */
    private synchronized void removeSubscriber(SubscriberType subscriber)
    {
        if (subscribers.contains(subscriber))
        {
            ArrayList<SubscriberType> newSubscribers = new ArrayList<>(subscribers);
            newSubscribers.remove(subscriber);
            subscribers = newSubscribers;
        }
    }

    /**
     * Invokes the given {@link Consumer} for all subscribers in this registry
     *
     * @param subscriberConsumer the consumer to be invoked
     */
    public void callWithEachSubscriber(Consumer<SubscriberType> subscriberConsumer)
    {
        subscribers.forEach(subscriberConsumer);
    }
}
