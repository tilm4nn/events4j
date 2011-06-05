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
package net.objectzoo.delegates.impl;

import java.util.concurrent.Executor;

import net.objectzoo.delegates.Action3;
import net.objectzoo.delegates.Action3Async;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.adapters.Action3ToAction3Async;
import net.objectzoo.delegates.helpers.AsyncExecutor;

/**
 * This is an abstract support class that can be used to quickly implement asynchronous actions.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action3#invoke(Object, Object, Object)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * {@code ActionAsyncSupport} instances. If no explicit executor is given the a default executor is
 * used. The default executor can be set using the
 * {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is created automatically by the
 * {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Action3Async}'s first parameter
 * @param <T2>
 *        The type of the {@code Action3Async}'s second parameter
 * @param <T3>
 *        The type of the {@code Action3Async}'s third parameter
 */
public abstract class Action3AsyncSupport<T1, T2, T3> implements Action3<T1, T2, T3>,
	Action3Async<T1, T2, T3>
{
	private final Action3Async<T1, T2, T3> asyncDelegate;
	
	/**
	 * Creates a new {@code Action3AsyncSupport} that uses the default executor
	 */
	public Action3AsyncSupport()
	{
		this(null);
	}
	
	/**
	 * Create a new {@code Action3AsyncSupport} that uses the given executor
	 * 
	 * @param executor
	 *        the {@link Executor} to use for asynchronous invocations
	 */
	public Action3AsyncSupport(Executor executor)
	{
		asyncDelegate = new Action3ToAction3Async<T1, T2, T3>(this, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void invoke(T1 parameter1, T2 parameter2, T3 parameter3);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 T1 parameter1, T2 parameter2, T3 parameter3)
	{
		return asyncDelegate.beginInvoke(callback, asyncState, parameter1, parameter2, parameter3);
	}
}
