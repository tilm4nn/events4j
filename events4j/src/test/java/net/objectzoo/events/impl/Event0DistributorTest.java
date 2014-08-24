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

package net.objectzoo.events.impl;

import net.objectzoo.delegates.Action0;
import net.objectzoo.events.helpers.EventSubscriberRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event0DistributorTest
{
    private final EventSubscriberRegistry registryMock = mock(EventSubscriberRegistry.class);
    private final Event0Distributor subject = new Event0Distributor();

    @Before
    public void setMock()
    {
        subject.registry = registryMock;
    }

    @Test
    public void subscribe_calls_subscriber_holder()
    {
        Action0 subscriber = someAction0();

        subject.subscribe(subscriber);

        verify(registryMock).subscribe(subscriber);
    }

    @Test
    public void unsubscribe_calls_subscriber_holder()
    {
        Action0 subscriber = someAction0();

        subject.unsubscribe(subscriber);

        verify(registryMock).unsubscribe(subscriber);
    }

    @Test
    public void accept_calls_subscribers()
    {
        subject.start();

        ArgumentCaptor<Consumer<Action0>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(
            Consumer.class);
        verify(registryMock).callWithEachSubscriber(consumerArgument.capture());

        Action0 subscriberMock = mock(Action0.class);

        consumerArgument.getValue().accept(subscriberMock);
        verify(subscriberMock).start();
    }

    private static Action0 someAction0()
    {
        return new Action0()
        {
            @Override
            public void start()
            {
            }
        };
    }
}
