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
package net.objectzoo.events.helpers;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * This helper class is a holder that is used by the event callers to store the subscribers that
 * subscribed for the event.
 * 
 * @author tilmann
 * 
 * @param <SubscriberType>
 *        the type of subscriber this registry holds
 */
public class EventSubscriberHolder<SubscriberType>
{
	Optional<SubscriberType> subscriber = Optional.empty();
	
	/**
	 * Subscribe the given subscriber to this Event
	 * 
	 * @param subscriber
	 *        the subscriber to be invoked when this Event signals
	 * @throws IllegalStateException
	 *         if this {@code EventSubscriberHolder} already has a subscriber
	 */
	public void subscribe(SubscriberType subscriber) throws IllegalArgumentException,
		IllegalStateException
	{
		Objects.requireNonNull(subscriber);
		
		if (this.subscriber.isPresent())
		{
			throw new IllegalStateException(
				"This event already has a subscriber action and allows only a single one.");
		}
		
		this.subscriber = Optional.of(subscriber);
	}
	
	/**
	 * Unsubscribe the given subscriber from this Event
	 * 
	 * @param subscriber
	 *        the subscriber to be unsubscribed
	 * @throws IllegalStateException
	 *         if another subscriber than the given one is subscribed to this
	 *         {@code EventSubscriberHolder}
	 */
	public void unsubscribe(SubscriberType subscriber) throws IllegalArgumentException,
		IllegalStateException
	{
		Objects.requireNonNull(subscriber);
		
		if (this.subscriber.orElse(null) != subscriber)
		{
			throw new IllegalStateException("The given action is not subscribed to this event.");
		}
		
		this.subscriber = Optional.empty();
	}
	
	/**
	 * Invokes the given {@link Consumer} with the subscriber if present.
	 * 
	 * @param subscriberConsumer
	 *        the consumer to be invoked
	 */
	public void callWithSubscriber(Consumer<SubscriberType> subscriberConsumer)
	{
		Objects.requireNonNull(subscriberConsumer);
		
		this.subscriber.ifPresent(subscriberConsumer);
	}
}
