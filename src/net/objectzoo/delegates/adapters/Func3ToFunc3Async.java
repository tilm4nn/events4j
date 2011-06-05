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

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import net.objectzoo.delegates.Func3;
import net.objectzoo.delegates.Func3Async;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.helpers.AsyncExecutor;

/**
 * An adapter that converts a conventional {@link Func3} to an {@link Func3Async}.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Func3#invoke(Object, Object, Object)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T1>
 *        The type of the {@code Func3}'s first parameter
 * @param <T2>
 *        The type of the {@code Func3}'s second parameter
 * @param <T3>
 *        The type of the {@code Func3}'s third parameter
 * @param <R>
 *        The type of the {@code Func3}'s return value
 */
public class Func3ToFunc3Async<T1, T2, T3, R> implements Func3Async<T1, T2, T3, R>
{
	private final Func3<T1, T2, T3, R> func;
	private final AsyncExecutor asyncExecutor;
	
	/**
	 * Converts the given {@link Func3} to the interface {@link Func3Async} using the default
	 * executor
	 * 
	 * @param func
	 *        the func to be converted
	 */
	public Func3ToFunc3Async(Func3<T1, T2, T3, R> func)
	{
		this(func, null);
	}
	
	/**
	 * Converts the given {@link Func3} to the interface {@link Func3Async} and executes
	 * asynchronous call using the given {@link Executor}
	 * 
	 * @param func
	 *        the func to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public Func3ToFunc3Async(Func3<T1, T2, T3, R> func, Executor executor)
	{
		this.func = func;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FuncAsyncResult<R> beginInvoke(FuncAsyncCallback<? super R> callback, Object asyncState,
										  final T1 parameter1, final T2 parameter2,
										  final T3 parameter3)
	{
		Callable<R> callable = new Callable<R>()
		{
			@Override
			public R call() throws Exception
			{
				return func.invoke(parameter1, parameter2, parameter3);
			}
		};
		
		return asyncExecutor.execute(callable, callback, asyncState);
	}
}
