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
package net.objectzoo.delegates.helpers;

import java.util.concurrent.Executor;

import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.delegates.DynamicActionAsync;
import net.objectzoo.delegates.adapters.DynamicActionToDynamicActionAsync;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * This is an abstract support class that can be used to quickly implement asynchronous dynamic
 * actions.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link DynamicAction#dynamicInvoke(Object...)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * {@code ActionAsyncSupport} instances. If no explicit executor is given the a default executor is
 * used. The default executor can be set using the
 * {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is created automatically by the
 * {@link AsyncExecutor}.
 * 
 * @author tilmann
 */
public abstract class DynamicActionAsyncSupport implements DynamicAction, DynamicActionAsync
{
	private final DynamicActionAsync dynamicAsyncDelegate;
	
	/**
	 * Creates a new {@code DynamicActionAsyncSupport} that uses the default executor
	 */
	public DynamicActionAsyncSupport()
	{
		this(null);
	}
	
	/**
	 * Create a new {@code DynamicActionAsyncSupport} that uses the given executor
	 * 
	 * @param executor
	 *        the {@link Executor} to use for asynchronous invocations
	 */
	public DynamicActionAsyncSupport(Executor executor)
	{
		dynamicAsyncDelegate = new DynamicActionToDynamicActionAsync(this, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void dynamicInvoke(Object... params);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginDynamicInvoke(ActionAsyncCallback callback, Object asyncState,
												Object... params)
	{
		return dynamicAsyncDelegate.beginDynamicInvoke(callback, asyncState, params);
	}
}