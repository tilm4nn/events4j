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

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.ActionAsyncCallback;
import net.objectzoo.delegates.ActionAsyncResult;
import net.objectzoo.delegates.helpers.AsyncExecutor;

public class ActionToActionAsync<T> implements ActionAsync<T>
{
	
	private final Action<T> action;
	
	private final AsyncExecutor asyncExecutor;
	
	public ActionToActionAsync(Action<T> action)
	{
		this(action, null);
	}
	
	public ActionToActionAsync(Action<T> action, Executor executor)
	{
		this.action = action;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	@Override
	public ActionAsyncResult beginInvoke(ActionAsyncCallback callback, Object asyncState,
										 final T parameter)
	{
		
		Callable<Object> callable = new Callable<Object>()
		{
			@Override
			public Object call() throws Exception
			{
				action.invoke(parameter);
				return null;
			}
		};
		
		return asyncExecutor.execute(callable, new ActionCallbackToFuncCallback(callback),
			asyncState);
	}
}
