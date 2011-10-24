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

import net.objectzoo.delegates.Action4;
import net.objectzoo.delegates.Action4Async;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.impl.AsyncExecutor;

/**
 * An adapter that converts a conventional {@link Action4} to an {@link Action4Async}.
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
public class Action4ToAction4Async<T1, T2, T3, T4> implements Action4Async<T1, T2, T3, T4>
{
	private final Action4<T1, T2, T3, T4> action;
	private final AsyncExecutor asyncExecutor;
	
	/**
	 * Converts the given {@link Action4} to the interface {@link Action4Async} using the default
	 * executor
	 * 
	 * @param action
	 *        the action to be converted
	 */
	public Action4ToAction4Async(Action4<T1, T2, T3, T4> action)
	{
		this(action, null);
	}
	
	/**
	 * Converts the given {@link Action4} to the interface {@link Action4Async} and executes
	 * asynchronous call using the given {@link Executor}
	 * 
	 * @param action
	 *        the action to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public Action4ToAction4Async(Action4<T1, T2, T3, T4> action, Executor executor)
	{
		this.action = action;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 final T1 parameter1, final T2 parameter2,
										 final T3 parameter3, final T4 parameter4)
	{
		Callable<Object> callable = new Callable<Object>()
		{
			@Override
			public Object call() throws Exception
			{
				action.invoke(parameter1, parameter2, parameter3, parameter4);
				return null;
			}
		};
		
		return asyncExecutor.execute(callable, new ActionCallbackToFuncCallback(callback),
			asyncState);
	}
}
