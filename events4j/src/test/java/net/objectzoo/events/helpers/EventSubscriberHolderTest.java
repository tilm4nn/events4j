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

import net.objectzoo.delegates.Action0;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventSubscriberHolderTest
{
    private final EventSubscriberHolder<Action0> sut = new EventSubscriberHolder<>();

    private static Action0 someAction0()
    {
        // This does not work because the returned values are equal!
        // return () -> {};

        return new Action0()
        {
            @Override
            public void start()
            {
            }
        };
    }

    @Test(expected = NullPointerException.class)
    public void subscribe_throws_exception_for_null_subscriber()
    {
        sut.subscribe(null);
    }

    @Test(expected = IllegalStateException.class)
    public void subscribe_throws_exception_when_subscriber_is_already_set()
    {
        sut.subscribe(someAction0());

        sut.subscribe(someAction0());
    }

    @Test
    public void subscribe_subscribes_the_given_subscriber()
    {
        Action0 subscriber = someAction0();

        sut.subscribe(subscriber);

        assertThat(sut.subscriber.get(), is(subscriber));
    }

    @Test(expected = NullPointerException.class)
    public void unsubscribe_throws_exception_for_null_subscriber()
    {
        sut.unsubscribe(null);
    }

    @Test(expected = IllegalStateException.class)
    public void unsubscribe_throws_exception_when_another_subscriber_is_set()
    {
        sut.subscribe(someAction0());

        sut.unsubscribe(someAction0());
    }

    @Test
    public void unsubscribe_unsubscribes_the_given_subscriber()
    {
        Action0 subscriber = someAction0();
        sut.subscribe(subscriber);

        sut.unsubscribe(subscriber);

        assertFalse(sut.subscriber.isPresent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void callWithSubscriber_calls_given_consumer_when_subscribed()
    {
        Action0 subscriber = someAction0();
        sut.subscribe(subscriber);
        Consumer<Action0> actionConsumer = mock(Consumer.class);

        sut.callWithSubscriber(actionConsumer);

        verify(actionConsumer).accept(subscriber);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void callWithSubscriber_does_not_call_given_consumer_when_not_subscribed()
    {
        Consumer<Action0> actionConsumer = mock(Consumer.class);

        sut.callWithSubscriber(actionConsumer);

        Mockito.verifyZeroInteractions(actionConsumer);
    }

}
