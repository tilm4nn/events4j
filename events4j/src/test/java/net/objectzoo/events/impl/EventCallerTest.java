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

import net.objectzoo.delegates.Action;
import net.objectzoo.events.helpers.EventSubscriberHolder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventCallerTest
{
    private final EventSubscriberHolder holderMock = mock(EventSubscriberHolder.class);
    private final EventCaller subject = new EventCaller();

    @Before
    public void setMock()
    {
        subject.subscriberHolder = holderMock;
    }

    @Test
    public void subscribe_calls_subscriber_holder()
    {
        Action subscriber = someAction();

        subject.subscribe(subscriber);

        verify(holderMock).subscribe(subscriber);
    }

    @Test
    public void unsubscribe_calls_subscriber_holder()
    {
        Action subscriber = someAction();

        subject.unsubscribe(subscriber);

        verify(holderMock).unsubscribe(subscriber);
    }

    @Test
    public void accept_calls_subscriber()
    {
        Object argument1 = new Object();
        subject.accept(argument1);

        ArgumentCaptor<Consumer<Action>> consumerArgument = (ArgumentCaptor) ArgumentCaptor.forClass(
            Consumer.class);
        verify(holderMock).callWithSubscriber(consumerArgument.capture());

        Action subscriberMock = mock(Action.class);

        consumerArgument.getValue().accept(subscriberMock);
        verify(subscriberMock).accept(argument1);
    }

    private static Action someAction()
    {
        return new Action()
        {
            @Override
            public void accept(Object parameter1)
            {
            }
        };
    }
}
