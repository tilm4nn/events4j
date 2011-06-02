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
package net.objectzoo.events;

import net.objectzoo.delegates.DynamicAction;

/**
 * A {@code DynamicEvent} is a source of signals and/or information that can be observed by
 * subscribing {@link DynamicAction} instances to it.
 * 
 * When a {@code DynamicEvent} signals all subscribed {@code DynamicAction}s are invoked. The order
 * of of the invocation of the {@code DynamicAction}s and the behavior in case of an
 * {@link RuntimeException} during the invocation of an {@code DynamicAction} depends on the
 * concrete {@code DynamicEvent} implementation.
 * 
 * @author tilmann
 */
public interface DynamicEvent
{
	/**
	 * Subscribe the given {@link DynamicAction} to this {@code DynamicEvent}
	 * 
	 * @param action
	 *        the {@link DynamicAction} to be invoked when this {@code DynamicEvent} signals
	 */
	public void subscribe(DynamicAction action);
	
	/**
	 * Unsubscribe the given {@link DynamicAction} from this {@code DynamicEvent}
	 * 
	 * @param action
	 *        the {@link DynamicAction} to be unsubscribed
	 */
	public void unsubscribe(DynamicAction action);
}
