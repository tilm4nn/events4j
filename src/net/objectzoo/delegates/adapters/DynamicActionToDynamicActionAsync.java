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

import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.DynamicAction;
import net.objectzoo.delegates.DynamicActionAsync;
import net.objectzoo.delegates.helpers.AsyncExecutor;

/**
 * An adapter that converts a conventional {@link DynamicAction} to an {@link DynamicActionAsync}.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link DynamicAction#dynamicInvoke(Object...)} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 */
public class DynamicActionToDynamicActionAsync implements DynamicActionAsync
{
	private final DynamicAction dynamicAction;
	private final AsyncExecutor asyncExecutor;
	
	/**
	 * Converts the given {@link DynamicAction} to the interface {@link DynamicActionAsync} using
	 * the default executor
	 * 
	 * @param dynamicAction
	 *        the dynamicAction to be converted
	 */
	public DynamicActionToDynamicActionAsync(DynamicAction dynamicAction)
	{
		this(dynamicAction, null);
	}
	
	/**
	 * Converts the given {@link DynamicAction} to the interface {@link DynamicActionAsync} and
	 * executes asynchronous call using the given {@link Executor}
	 * 
	 * @param dynamicAction
	 *        the dynamicAction to be converted
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public DynamicActionToDynamicActionAsync(DynamicAction dynamicAction, Executor executor)
	{
		this.dynamicAction = dynamicAction;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionAsyncResult beginDynamicInvoke(ActionAsyncCallback callback, Object asyncState,
												final Object... params)
	{
		Callable<Object> callable = new Callable<Object>()
		{
			@Override
			public Object call() throws Exception
			{
				dynamicAction.dynamicInvoke(params);
				return null;
			}
		};
		
		return asyncExecutor.execute(callable, new ActionCallbackToFuncCallback(callback),
			asyncState);
	}
}
