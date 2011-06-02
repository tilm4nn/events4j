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

import net.objectzoo.delegates.Func2;
import net.objectzoo.delegates.Func2Async;
import net.objectzoo.delegates.FuncAsyncCallback;
import net.objectzoo.delegates.FuncAsyncResult;
import net.objectzoo.delegates.helpers.AsyncExecutor;

public class Func2ToFunc2Async<T1, T2, R> implements Func2Async<T1, T2, R>
{
	private final Func2<T1, T2, R> func;
	
	private final AsyncExecutor asyncExecutor;
	
	public Func2ToFunc2Async(Func2<T1, T2, R> func)
	{
		this(func, null);
	}
	
	public Func2ToFunc2Async(Func2<T1, T2, R> func, Executor executor)
	{
		this.func = func;
		this.asyncExecutor = new AsyncExecutor(executor);
	}
	
	@Override
	public FuncAsyncResult<R> beginInvoke(FuncAsyncCallback<? super R> callback, Object asyncState,
										  final T1 parameter1, final T2 parameter2)
	{
		Callable<R> callable = new Callable<R>()
		{
			@Override
			public R call() throws Exception
			{
				return func.invoke(parameter1, parameter2);
			}
		};
		
		return asyncExecutor.execute(callable, callback, asyncState);
	}
}
