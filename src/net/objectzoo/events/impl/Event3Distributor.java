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
import net.objectzoo.events.Event3;
import net.objectzoo.events.helpers.EventSubscriberRegisty;

public class Event3Distributor<T1, T2, T3> implements Action3<T1, T2, T3>, Event3<T1, T2, T3>
{
	
	private EventSubscriberRegisty<Action3<? super T1, ? super T2, ? super T3>> registry = new EventSubscriberRegisty<Action3<? super T1, ? super T2, ? super T3>>();
	
	@Override
	public void invoke(T1 parameter1, T2 parameter2, T3 parameter3)
	{
		for (Action3<? super T1, ? super T2, ? super T3> action : registry.getSubscribers())
		{
			action.invoke(parameter1, parameter2, parameter3);
		}
	}
	
	@Override
	public void subscribe(Action3<? super T1, ? super T2, ? super T3> action)
	{
		registry.subscribe(action);
	}
	
	@Override
	public void unsubscribe(Action3<? super T1, ? super T2, ? super T3> action)
	{
		registry.unsubscribe(action);
	}
	
}
