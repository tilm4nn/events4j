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
import net.objectzoo.events.Event;
import net.objectzoo.events.helpers.EventSubscriberRegisty;

public class EventDistributor<T> implements Action<T>, Event<T>
{
	
	private EventSubscriberRegisty<Action<? super T>> registry = new EventSubscriberRegisty<Action<? super T>>();
	
	@Override
	public void invoke(T parameter)
	{
		for (Action<? super T> action : registry.getSubscribers())
		{
			action.invoke(parameter);
		}
	}
	
	@Override
	public void subscribe(Action<? super T> action)
	{
		registry.subscribe(action);
	}
	
	@Override
	public void unsubscribe(Action<? super T> action)
	{
		registry.unsubscribe(action);
	}
	
}
