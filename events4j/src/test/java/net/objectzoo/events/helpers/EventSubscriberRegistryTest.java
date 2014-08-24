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

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EventSubscriberRegistryTest
{
    private EventSubscriberRegistry<Action0> sut = new EventSubscriberRegistry<>();

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

    @Test
    public void subscribe_subscribes_the_given_subscriber()
    {
        Action0 subscriber = someAction0();

        sut.subscribe(subscriber);

        assertThat(sut.subscribers, hasItem(subscriber));
    }

    @Test(expected = NullPointerException.class)
    public void unsubscribe_throws_exception_for_null_subscriber()
    {
        sut.unsubscribe(null);
    }

    @Test
    public void unsubscribe_unsubscribes_the_given_subscriber()
    {
        Action0 subscriber = someAction0();
        sut.subscribe(subscriber);

        sut.unsubscribe(subscriber);

        assertThat(sut.subscribers, not(hasItem(subscriber)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void callWithEachSubscriber_calls_given_consumer_when_subscribed()
    {
        Action0 subscriber1 = someAction0();
        Action0 subscriber2 = someAction0();
        sut.subscribe(subscriber1);
        sut.subscribe(subscriber2);
        Consumer<Action0> actionConsumer = mock(Consumer.class);

        sut.callWithEachSubscriber(actionConsumer);

        verify(actionConsumer).accept(subscriber1);
        verify(actionConsumer).accept(subscriber2);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void callWithEachSubscriber_does_not_call_given_consumer_when_not_subscribed()
    {
        Consumer<Action0> actionConsumer = mock(Consumer.class);

        sut.callWithEachSubscriber(actionConsumer);

        Mockito.verifyZeroInteractions(actionConsumer);
    }
}
