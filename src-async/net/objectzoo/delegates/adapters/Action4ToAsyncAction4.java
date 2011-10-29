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

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action4;
import net.objectzoo.delegates.Action4Async;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * An adapter that converts an {@link Action4} to a new {@link Action4}, that makes an asynchronous
 * calls to the original Action0 upon invocation. This adapter provides no possibility to check for
 * the completion of the original action nor to retrieve thrown exceptions. Thus it is recommended
 * to use {@link Action4ToAction4Async} adapter instead to achieve a similar result.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action4#invoke(Object, Object, Object, Object)} method.
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
 * @param <T3>
 *        The type of the {@code Action}'s third parameter
 * @param <T4>
 *        The type of the {@code Action}'s fourth parameter
 */
public class Action4ToAsyncAction4<T1, T2, T3, T4> implements Action4<T1, T2, T3, T4>
{
	private final Action4Async<T1, T2, T3, T4> actionAsync;
	
	/**
	 * Makes the given {@link Action} asynchronous using the default executor
	 * 
	 * @param action
	 *        the action to be called asynchronously
	 */
	public Action4ToAsyncAction4(Action4<T1, T2, T3, T4> action)
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
	public Action4ToAsyncAction4(Action4<T1, T2, T3, T4> action, Executor executor)
	{
		actionAsync = new Action4ToAction4Async<T1, T2, T3, T4>(action, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invoke(T1 parameter1, T2 parameter2, T3 parameter3, T4 parameter4)
	{
		actionAsync.beginInvoke(null, null, parameter1, parameter2, parameter3, parameter4);
	}
}
