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

import net.objectzoo.delegates.Action;

/**
 * An {@code Event} is a source of signals and/or information that can be observed by subscribing
 * {@link Action} instances to it.
 * 
 * When an {@code Event} signals all subscribed {@code Action}s are invoked. The order of of the
 * invocation of the {@code Action}s and the behavior in case of an {@link RuntimeException} during
 * the invocation of an {@code Action} depends on the concrete {@code Event} implementation.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The type of the information parameter the event provides
 */
public interface Event<T>
{
	/**
	 * Subscribe the given {@link Action} to this {@code Event}
	 * 
	 * @param action
	 *        the {@link Action} to be invoked when this {@code Event} signals
	 * @throws IllegalArgumentException
	 *         if he given action to be subscribed is {@code null}
	 */
	public void subscribe(Action<? super T> action) throws IllegalArgumentException;
	
	/**
	 * Unsubscribe the given {@link Action} from this {@code Event}
	 * 
	 * @param action
	 *        the {@link Action} to be unsubscribed
	 * @throws IllegalArgumentException
	 *         if he given action to be unsubscribed is {@code null}
	 */
	public void unsubscribe(Action<? super T> action) throws IllegalArgumentException;
}
