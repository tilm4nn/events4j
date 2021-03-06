/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:events4j@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.objectzoo.events.impl;

import net.objectzoo.delegates.Action3;
import net.objectzoo.events.helpers.EventSubscriberRegistry;

/**
 * The {@code Event3Distributor} is a {@link Event3Delegate} implementation that allows event
 * distribution to multiple subscribers. It is a helper class that encapsulates all the logic
 * required to provide events in other classes. Subscriber invocations are performed in the order of
 * subscription.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the first information parameter the event provides
 * @param <T2>
 *        The type of the second information parameter the event provides
 * @param <T3>
 *        The type of the third information parameter the event provides
 */
public class Event3Distributor<T1, T2, T3> implements Event3Delegate<T1, T2, T3>
{
	EventSubscriberRegistry<Action3<? super T1, ? super T2, ? super T3>> registry = new EventSubscriberRegistry<>();
	
	/**
	 * This {@code invoke} implementation invokes all event subscribers in the order they have been
	 * subscribed.
	 * 
	 * @param parameter1
	 *        the first parameter to invoke the subscribers with
	 * @param parameter2
	 *        the second parameter to invoke the subscribers with
	 * @param parameter3
	 *        the second parameter to invoke the subscribers with
	 */
	@Override
	public void accept(T1 parameter1, T2 parameter2, T3 parameter3)
	{
		registry.callWithEachSubscriber(Action3.boundAcceptingConsumer(parameter1, parameter2, parameter3));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void subscribe(Action3<? super T1, ? super T2, ? super T3> action)
		throws IllegalArgumentException
	{
		registry.subscribe(action);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void unsubscribe(Action3<? super T1, ? super T2, ? super T3> action)
		throws IllegalArgumentException
	{
		registry.unsubscribe(action);
	}
	
}
