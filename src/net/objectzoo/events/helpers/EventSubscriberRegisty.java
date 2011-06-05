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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This helper class is a registry that is used by the event distributors to store the actions that
 * registered for the event.
 * 
 * @author tilmann
 * 
 * @param <ActionType>
 *        the type of action this registry holds
 */
public class EventSubscriberRegisty<ActionType>
{
	private List<ActionType> subscribers = new CopyOnWriteArrayList<ActionType>();
	
	/**
	 * Adds the given action to this registry if it is not allready subscribed. This is checked
	 * using the {@link Object#equals(Object)} of the action.
	 * 
	 * @param action
	 *        the action to be added
	 */
	public void subscribe(ActionType action)
	{
		if (!subscribers.contains(action))
		{
			subscribers.add(action);
		}
	}
	
	/**
	 * Removes the given action from this registry.
	 * 
	 * @param action
	 *        the action to be removed
	 */
	public void unsubscribe(ActionType action)
	{
		subscribers.remove(action);
	}
	
	/**
	 * Returns all subscribed actions in this registry
	 * 
	 * @return all subscribed actions
	 */
	public Collection<ActionType> getSubscribers()
	{
		return subscribers;
	}
}
