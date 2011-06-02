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

import net.objectzoo.delegates.DynamicFunc;
import net.objectzoo.delegates.DynamicFuncAsync;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.helpers.AsyncExecutor;

public class DynamicFuncToDynamicFuncAsync implements DynamicFuncAsync
{
	private final DynamicFunc dynamicFunc;
	
	private final AsyncExecutor asyncExecutor;
	
	public DynamicFuncToDynamicFuncAsync(DynamicFunc dynamicFunc)
	{
		this(dynamicFunc, null);
	}
	
	public DynamicFuncToDynamicFuncAsync(DynamicFunc dynamicFunc, Executor executor)
	{
		this.dynamicFunc = dynamicFunc;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	@Override
	public FuncAsyncResult<?> beginDynamicInvoke(FuncAsyncCallback<Object> callback,
												 Object asyncState, final Object... params)
	{
		
		Callable<Object> callable = new Callable<Object>()
		{
			@Override
			public Object call() throws Exception
			{
				return dynamicFunc.dynamicInvoke(params);
			}
		};
		
		return asyncExecutor.execute(callable, callback, asyncState);
	}
}
