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

import net.objectzoo.delegates.Action4;
import net.objectzoo.events.helpers.EventSubscriberHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Event4CallerTest
{
    private final EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
    private final Event4Caller subject = new Event4Caller();

    @Before
    public void setMock()
    {
        subject.subscriberHolder = holderMock;
    }

    @Test
    public void subscribe_calls_subscriber_holder()
    {
        Action4 subscriber = someAction4();

        subject.subscribe(subscriber);

        verify(holderMock).subscribe(subscriber);
    }

    @Test
    public void unsubscribe_calls_subscriber_holder()
    {
        Action4 subscriber = someAction4();

        subject.unsubscribe(subscriber);

        verify(holderMock).unsubscribe(subscriber);
    }

    @Test
    public void accept_calls_subscriber()
    {
        Object argument1 = new Object();
        Object argument2 = new Object();
        Object argument3 = new Object();
        Object argument4 = new Object();
        subject.accept(argument1, argument2, argument3, argument4);

        ArgumentCaptor<Consumer<Action4>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(
            Consumer.class);
        verify(holderMock).callWithSubscriber(consumerArgument.capture());

        Action4 subscriberMock = mock(Action4.class);

        consumerArgument.getValue().accept(subscriberMock);
        verify(subscriberMock).accept(argument1, argument2, argument3, argument4);
    }

    private static Action4 someAction4()
    {
        return new Action4()
        {
            @Override
            public void accept(Object parameter1, Object parameter2, Object parameter3,
                               Object parameter4)
            {
            }
        };
    }
}
