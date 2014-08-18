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
package net.objectzoo.delegates.adapters;

import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action2;
import net.objectzoo.delegates.Action2Async;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * An adapter that converts an {@link Action2} or {@link BiConsumer} to a new {@link Action2}, that
 * makes an asynchronous calls to the original Action0 upon invocation. This adapter provides no
 * possibility to check for the completion of the original action nor to retrieve thrown exceptions.
 * Thus it is recommended to use {@link Action2ToAction2Async} adapter instead to achieve a similar
 * result.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action2#accept(Object, Object)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Action}'s first parameter
 * @param <T2>
 *        The type of the {@code Action}'s second parameter
 */
public class Action2ToAsyncAction2<T1, T2> implements Action2<T1, T2>
{
	private final Action2Async<T1, T2> actionAsync;
	
	/**
	 * Makes the given {@link Action} asynchronous using the default executor
	 * 
	 * @param action
	 *        the action to be called asynchronously
	 */
	public Action2ToAsyncAction2(BiConsumer<T1, T2> action)
	{
		this(action, null);
	}
	
	/**
	 * Makes the given {@link Action} asynchronous using the given executor
	 * 
	 * @param action
	 *        the action to be called asynchronously
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public Action2ToAsyncAction2(BiConsumer<T1, T2> action, Executor executor)
	{
		actionAsync = new Action2ToAction2Async<T1, T2>(action, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(T1 parameter1, T2 parameter2)
	{
		actionAsync.beginAccept(null, null, parameter1, parameter2);
	}
}
