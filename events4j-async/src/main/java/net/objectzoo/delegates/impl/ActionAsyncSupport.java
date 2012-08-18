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

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.adapters.ActionToActionAsync;

/**
 * This is an abstract support class that can be used to quickly implement asynchronous actions.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action#invoke(Object)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * {@code ActionAsyncSupport} instances. If no explicit executor is given the a default executor is
 * used. The default executor can be set using the
 * {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is created automatically by the
 * {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The type of the {@code ActionAsync}'s parameter
 */
public abstract class ActionAsyncSupport<T> implements Action<T>, ActionAsync<T>
{
	private final ActionAsync<T> asyncDelegate;
	
	/**
	 * Creates a new {@code ActionAsyncSupport} that uses the default executor
	 */
	public ActionAsyncSupport()
	{
		this(null);
	}
	
	/**
	 * Create a new {@code ActionAsyncSupport} that uses the given executor
	 * 
	 * @param executor
	 *        the {@link Executor} to use for asynchronous invocations
	 */
	public ActionAsyncSupport(Executor executor)
	{
		asyncDelegate = new ActionToActionAsync<T>(this, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void invoke(T parameter);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 T parameter)
	{
		return asyncDelegate.beginInvoke(callback, asyncState, parameter);
	}
}
