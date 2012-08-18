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

import net.objectzoo.delegates.Action4;

/**
 * An {@code Event4} is a source of signals and/or information that can be observed by subscribing
 * {@link Action4} instances to it.
 * 
 * When an {@code Event4} signals all subscribed {@code Action4}s are invoked. The order of of the
 * invocation of the {@code Action4}s and the behavior in case of an {@link RuntimeException} during
 * the invocation of an {@code Action4} depends on the concrete {@code Event4} implementation.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the first information parameter the event provides
 * @param <T2>
 *        The type of the second information parameter the event provides
 * @param <T3>
 *        The type of the third information parameter the event provides
 * @param <T4>
 *        The type of the fourth information parameter the event provides
 */
public interface Event4<T1, T2, T3, T4>
{
	/**
	 * Subscribe the given {@link Action4} to this {@code Event4}
	 * 
	 * @param action
	 *        the {@link Action4} to be invoked when this {@code Event4} signals
	 * @throws IllegalArgumentException
	 *         if he given action to be subscribed is {@code null}
	 */
	public void subscribe(Action4<? super T1, ? super T2, ? super T3, ? super T4> action)
		throws IllegalArgumentException;
	
	/**
	 * Unsubscribe the given {@link Action4} from this {@code Event4}
	 * 
	 * @param action
	 *        the {@link Action4} to be unsubscribed
	 * @throws IllegalArgumentException
	 *         if he given action to be unsubscribed is {@code null}
	 */
	public void unsubscribe(Action4<? super T1, ? super T2, ? super T3, ? super T4> action)
		throws IllegalArgumentException;
}
