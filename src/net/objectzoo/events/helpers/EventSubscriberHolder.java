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

/**
 * This helper class is a holder that is used by the event callers to store the action that
 * subscribed for the event.
 * 
 * @author tilmann
 * 
 * @param <ActionType>
 *        the type of action this registry holds
 */
public class EventSubscriberHolder<ActionType>
{
	private ActionType subscriber;
	
	/**
	 * Subscribe the given Action to this Event
	 * 
	 * @param action
	 *        the Action to be invoked when this Event signals
	 * @throws IllegalStateException
	 *         if this {@code EventSubscriberHolder} already has a subscriber
	 * @throws IllegalArgumentException
	 *         if he given action to be subscribed is {@code null}
	 */
	public void subscribe(ActionType action) throws IllegalArgumentException, IllegalStateException
	{
		if (action == null)
		{
			throw new IllegalArgumentException("null is not a legal Action to be subscribed");
		}
		if (subscriber != null)
		{
			throw new IllegalStateException(
				"This event already has a subscriber action and allows only a single one.");
		}
		
		subscriber = action;
	}
	
	/**
	 * Unsubscribe the given Action from this Event
	 * 
	 * @param action
	 *        the Action to be unsubscribed
	 * @throws IllegalStateException
	 *         if another action than the given one is subscribed to this
	 *         {@code EventSubscriberHolder}
	 * @throws IllegalArgumentException
	 *         if he given action to be unsubscribed is {@code null}
	 */
	public void unsubscribe(ActionType action) throws IllegalArgumentException,
		IllegalStateException
	{
		if (action == null)
		{
			throw new IllegalArgumentException("null is not a legal Action to be unsubscribed");
		}
		if (subscriber != action)
		{
			throw new IllegalStateException("The given action is not subscribed to this event.");
		}
		
		subscriber = null;
	}
	
	/**
	 * Get the subscriber stored in this {@code EventSubscriberHolder}
	 * 
	 * @return the {@code Action} subscribed or {@code null} if no subscriber is present
	 */
	public ActionType getSubscriber()
	{
		return subscriber;
	}
}
