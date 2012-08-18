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

import net.objectzoo.delegates.Action;
import net.objectzoo.events.helpers.EventSubscriberHolder;

/**
 * The {@code EventCaller} is a {@link EventDelegate} implementation that allows event distribution
 * to a single subscriber. It is a helper class that encapsulates all the logic required to provide
 * events in other classes. Subscription of excessive subscribers results in
 * {@link IllegalStateException}s to be thrown.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The type of the information parameter the event provides
 */
public class EventCaller<T> implements EventDelegate<T>
{
	private EventSubscriberHolder<Action<? super T>> subscriberHolder = new EventSubscriberHolder<Action<? super T>>();
	
	/**
	 * This {@code invoke} implementation invokes the sole subscriber if present.
	 * 
	 * @param parameter
	 *        the parameter to invoke the subscriber with
	 */
	@Override
	public void invoke(T parameter)
	{
		Action<? super T> subscriber = subscriberHolder.getSubscriber();
		if (subscriber != null)
		{
			subscriber.invoke(parameter);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalStateException
	 *         if this {@code EventSubscriberHolder} already has a subscriber
	 */
	@Override
	public void subscribe(Action<? super T> action) throws IllegalArgumentException,
		IllegalStateException
	{
		subscriberHolder.subscribe(action);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalStateException
	 *         if another action than the given one is subscribed to this
	 *         {@code EventSubscriberHolder}
	 */
	@Override
	public void unsubscribe(Action<? super T> action) throws IllegalArgumentException,
		IllegalStateException
	{
		subscriberHolder.unsubscribe(action);
	}
}
