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

import net.objectzoo.delegates.Func;
import net.objectzoo.delegates.FuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.adapters.FuncToFuncAsync;

/**
 * This is an abstract support class that can be used to quickly implement asynchronous funcs.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Func#invoke(Object)} method.
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
 *        The type of the {@code FuncAsync}'s parameter
 * @param <R>
 *        The type of the funcs result
 */
public abstract class FuncAsyncSupport<T, R> implements Func<T, R>, FuncAsync<T, R>
{
	private final FuncAsync<T, R> asyncDelegate;
	
	/**
	 * Creates a new {@code FuncAsyncSupport} that uses the default executor
	 */
	public FuncAsyncSupport()
	{
		this(null);
	}
	
	/**
	 * Create a new {@code FuncAsyncSupport} that uses the given executor
	 * 
	 * @param executor
	 *        the {@link Executor} to use for asynchronous invocations
	 */
	public FuncAsyncSupport(Executor executor)
	{
		asyncDelegate = new FuncToFuncAsync<T, R>(this, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract R invoke(T parameter);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FuncAsyncResult<R> beginInvoke(FuncAsyncCallback<? super R> callback, Object asyncState,
										  T parameter)
	{
		return asyncDelegate.beginInvoke(callback, asyncState, parameter);
	}
}
