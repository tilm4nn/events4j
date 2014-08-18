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

import java.util.function.Consumer;

/**
 * An {@code Event} is a source of signals and/or information that can be observed by subscribing
 * {@link Consumer} instances to it.
 * 
 * When an {@code Event} signals all subscribed {@code Consumer}s are invoked. The order of of the
 * invocation of the {@code Consumer}s and the behavior in case of an {@link RuntimeException}
 * during the invocation of a {@code Consumer} depends on the concrete {@code Event} implementation.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The type of the information parameter the event provides
 */
public interface Event<T>
{
	/**
	 * Subscribe the given {@link Consumer} to this {@code Event}
	 * 
	 * @param consumer
	 *        the {@link Consumer} to be invoked when this {@code Event} signals
	 */
	public void subscribe(Consumer<? super T> consumer) throws IllegalArgumentException;
	
	/**
	 * Unsubscribe the given {@link Consumer} from this {@code Event}
	 * 
	 * @param consumer
	 *        the {@link Consumer} to be unsubscribed
	 */
	public void unsubscribe(Consumer<? super T> consumer) throws IllegalArgumentException;
}
